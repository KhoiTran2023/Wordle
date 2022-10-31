import gspread, stdiomask
from oauth2client.service_account import ServiceAccountCredentials

import smtplib, ssl, email
from email import encoders 
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
import time

scope = [
    'https://www.googleapis.com/auth/spreadsheets',
    'https://www.googleapis.com/auth/drive'
]
creds = ServiceAccountCredentials.from_json_keyfile_name('client_secret.json', scope)
client = gspread.authorize(creds)
sheet = client.open("ProjectTshirt").sheet1

def checkData(row,col):
    return sheet.cell(row,col).value == "unsent"

def sendEmails(re, sender_email, password, part, filename):
    sender_email = sender_email
    password = password
    receiver_email = re
    msg = MIMEMultipart("alternative") #change
    msg["Subject"] = "Testing" #change
    msg["From"] = sender_email
    msg["To"] = receiver_email

    #adding attachment
    msg.attach(part)
    if filename != "":
        with open(filename, "rb") as attachment:
            part = MIMEBase("application", "octet-stream")
            part.set_payload(attachment.read())
        encoders.encode_base64(part)

    #set mail headers
    if filename != "":
        part.add_header(
            "Content-Disposition",
            "attachment", filename = filename
        )
    msg.attach(part)

    #Create secure SMTP connection and sending email
    context = ssl.create_default_context()
    with smtplib.SMTP_SSL("smtp.gmail.com", 465, context = context) as server:
        server.login(sender_email, password)
        server.sendmail(sender_email, receiver_email, msg.as_string())

def run():
    sender_email = input("Sender email address: ") #takes user input of the sender
    password = stdiomask.getpass() #user input for password
    conf_pass = stdiomask.getpass() #confirmation of password if you mess up twice thats on you lol
    while password != conf_pass:
        print("Passwords don't match, try again.")
        password = stdiomask.getpass()
        conf_pass = stdiomask.getpass()

    conf_attachment = input("Send file attachment? YES or NO ")
    if conf_attachment == "YES":
        part2 = input("What is your file name? Include the extension ") #insert virus here and change
    else:
        part2 = ""

    firstname = input("What is your first name? ")
    lastname = input("What is your last name?")
    address = input("What is your address?")
    citystatezip = input("What is your city, state, and zip code, in that order?")
    school = input("What is your full school name?")
    last_stop = int(input("What number row did you stop at?"))

    print("Working...")
    email_count_hour = 0
    for row in range(last_stop,sheet.row_count+1):
        if checkData(row,2):
            collegename = sheet.cell(row,3).value
            part1 = MIMEText("Hi, my name is {fname},\nI am a student from {sname}, and I am researching colleges. I am only a junior, but have been looking quite intensely for a place to further my career, and {cname} seemed to be a good fit for me. I was really interested in the diversity of courses you guys offered, and the warm community that seems so characteristic of your school. I was wondering if you might be interested in sending me some additional information about the student experience, and a token of appreciation for prospective students? I would greatly appreciate any way of representing {cname}, like mugs, tshirts, and socks. If you are interested, I have attached my contact information and address. Thank you so much for your time.\n\nAll the best, \n{fname} {lname}\n\n{cinfo}\n{ainfo}\n{csz}".format(fname = firstname, cname = collegename, lname = lastname, sname = school, cinfo = sender_email, ainfo = address, csz = citystatezip), "plain")
            sendEmails(sheet.cell(row,1).value, sender_email, password, part1, part2)
            sheet.update_cell(row,2,"sent")
            email_count_hour+=1
            print("Queued {cname}".format(cname = collegename))
            if email_count_hour == 19:
                time.sleep(3600)
                email_count_hour = 0

    print("Run complete")

run()