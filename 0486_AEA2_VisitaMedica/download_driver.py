import urllib.request
import ssl
import os

if not os.path.exists('lib'):
    os.makedirs('lib')

url = 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.1.0/mysql-connector-j-9.1.0.jar'
ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

try:
    print("Downloading...")
    with urllib.request.urlopen(url, context=ctx) as response, open('lib/mysql-connector-j-9.1.0.jar', 'wb') as out_file:
        data = response.read()
        out_file.write(data)
    print("Downloaded successfully.")
except Exception as e:
    print(f"Error: {e}")
