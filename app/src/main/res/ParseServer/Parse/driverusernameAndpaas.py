import json,http.client
connection = http.client.HTTPConnection('localhost', 1337)
connection.connect()

# pos
#ss = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
#time


connection.request('POST', '/parse/batch', json.dumps({
       "requests": [
               
         {
           "method": "POST",
           "path": "/parse/users",
           "body": {
             "active": "",
             "username": "1",
             "password": "1",
             "objectid": "",
             "studentOrDriver": "driver"
           }
         },
           
           {
           "method": "POST",
           "path": "/parse/users",
           "body": {
             "active": "",
             "username": "2",
             "password": "2",
             "objectid": "",
             "studentOrDriver": "driver"
           }
         },
           
                   {
           "method": "POST",
           "path": "/parse/users",
           "body": {
             "active": "",
             "username": "3",
             "password": "3",
             "objectid": "",
             "studentOrDriver": "driver"
           }
         },
           
                   {
           "method": "POST",
           "path": "/parse/users",
           "body": {
             "active": "",
             "username": "4",
             "password": "4",
             "objectid": "",
             "studentOrDriver": "driver"
           }
         },
           
                   {
           "method": "POST",
           "path": "/parse/users",
           "body": {
             "active": "",
             "username": "5",
             "password": "5",
             "objectid": "",
             "studentOrDriver": "driver"
           }
         },
           
                   {
           "method": "POST",
           "path": "/parse/users",
           "body": {
             "active": "",
             "username": "6",
             "password": "6",
             "objectid": "",
             "studentOrDriver": "driver"
           }
         },
           
                   {
           "method": "POST",
           "path": "/parse/users",
           "body": {
             "active": "",
             "username": "7",
             "password": "7",
             "objectid": "",
             "studentOrDriver": "driver"
           }
         },
           
           
                    {
           "method": "POST",
           "path": "/parse/users",
           "body": {
             "active": "",
             "username": "8",
             "password": "8",
             "objectid": "",
             "studentOrDriver": "driver"
           }
         },
           
       ]

     }), {
       "X-Parse-Application-Id": "12345",
       #"X-Parse-REST-API-Key": "${REST_API_KEY}",
       "Content-Type": "application/json"
     })
result = json.loads(connection.getresponse().read())
print (result)