import json,http.client
connection = http.client.HTTPConnection('localhost', 1337)
connection.connect()

# pos
ss = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
#time
num = ['Main Campus to Permanent Campus',
      '07.30 am  MC  PC  Shurjomukhi-2',
      '08.30 am  MC  PC  Shurjomukhi-5',
      '09.30 am  MC  PC  Shurjomukhi-9',
       '10.30 am  MC  PC  Shurjomukhi-2',
        '11.30 am  MC  PC  Shurjomukhi-5',
        '02.30 pm  MC  PC  Shurjomukhi-9',
        '03.30 pm  MC  PC  Shurjomukhi-2',
        '04.30 pm  MC  PC  Shurjomukhi-5',
        '05.30 pm  MC  PC  Shurjomukhi-9',
        'Permanent Campus to Main Campus',
        '07.30 am  PC  MC  Shurjomukhi-9',
        '08.30 am  PC  MC  Shurjomukhi-14',
        '09.30 am  PC  MC  Shurjomukhi-2',
        '10.30 am  PC  MC  Shurjomukhi-5',
        '11.30 am  PC  MC  Shurjomukhi-9',
        '02.30 pm  PC  MC  Shurjomukhi-2',
        '03.30 pm  PC  MC  Shurjomukhi-5',
        '04.30 pm  PC  MC  Shurjomukhi-9',
        '05.30 pm  PC  MC  Shurjomukhi-2']

connection.request('POST', '/parse/batch', json.dumps({
       "requests": [
               
               
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[0],
             "pos": ss[0]
           }
         },
           
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[1],
             "pos": ss[1]
           }
         },
           
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[2],
             "pos": ss[2]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[3],
             "pos": ss[3]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[4],
             "pos": ss[4]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[5],
             "pos": ss[5]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[6],
             "pos": ss[6]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[7],
             "pos": ss[7]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[8],
             "pos": ss[8]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[9],
             "pos": ss[9]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[10],
             "pos": ss[10]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[11],
             "pos": ss[11]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[12],
             "pos": ss[12]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[13],
             "pos": ss[13]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[14],
             "pos": ss[14]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[15],
             "pos": ss[15]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[16],
             "pos": ss[16]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[17],
             "pos": ss[17]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[18],
             "pos": ss[18]
           }
         },
           
         {
           "method": "POST",
           "path": "/parse/classes/Schedule",
           "body": {
             "time": num[19],
             "pos": ss[19]
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