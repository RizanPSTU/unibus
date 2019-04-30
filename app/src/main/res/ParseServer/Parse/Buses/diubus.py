import json,http.client
connection = http.client.HTTPConnection('localhost', 1337)
connection.connect()

# pos
#ss = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
#time
bus = ['Surjomukhi 01 Route: UC - PC',
        'Surjomukhi 02 Route: MC - PC',
        'Surjomukhi 04 Route: Savar - PC - Mirpur-1',
        'Surjomukhi 05 Route: C&B - MC',
        'Surjomukhi 09 Route: Gazipur - PC',
        'Surjomukhi 11 Route: PC - UC',
        'Rojonigondha 01 Route: UC - MC',
        'Rojonigondha 02 Route: Mirpur-11 - MC - PC']

connection.request('POST', '/parse/batch', json.dumps({
       "requests": [
               
               
         {
           "method": "POST",
           "path": "/parse/classes/Buses",
           "body": {
             "active_status": "driverOFF",
             "busname": bus[0]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Buses",
           "body": {
             "active_status": "driverOFF",
             "busname": bus[1]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Buses",
           "body": {
             "active_status": "driverOFF",
             "busname": bus[2]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Buses",
           "body": {
             "active_status": "driverOFF",
             "busname": bus[3]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Buses",
           "body": {
             "active_status": "driverOFF",
             "busname": bus[4]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Buses",
           "body": {
             "active_status": "driverOFF",
             "busname": bus[5]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Buses",
           "body": {
             "active_status": "driverOFF",
             "busname": bus[6]
           }
         },
           
          {
           "method": "POST",
           "path": "/parse/classes/Buses",
           "body": {
             "active_status": "driverOFF",
             "busname": bus[7]
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