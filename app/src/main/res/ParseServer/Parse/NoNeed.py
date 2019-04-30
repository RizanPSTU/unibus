import json,http.client
import time
connection = http.client.HTTPConnection('localhost', 1337)
connection.connect()


obid = [
        'isqXNhGDG3',
        '3idu7CSIjs',
        'Pw9aUzbYSy',
        'fBuxNKEpPF',
        'AdbhNV7Hnr',
        'YsbxIxMQkT',
        'O4GpHYss3V',
        'TSKtHbAQAH'
       ]

while(1):
    
    connection = http.client.HTTPConnection('localhost', 1337)
    connection.connect()
    
    connection.request('PUT', '/parse/classes/Buses/'+obid[0]+'', json.dumps({
           "active_status": 'driverOFF'
         }), {
           "X-Parse-Application-Id": "12345",
           "Content-Type": "application/json"
         })
    result = json.loads(connection.getresponse().read())
    print (result)
    
    connection.request('PUT', '/parse/classes/Buses/'+obid[1]+'', json.dumps({
           "active_status": 'driverOFF'
         }), {
           "X-Parse-Application-Id": "12345",
           "Content-Type": "application/json"
         })
    result = json.loads(connection.getresponse().read())
    print (result)
    
    connection.request('PUT', '/parse/classes/Buses/'+obid[2]+'', json.dumps({
           "active_status": 'driverOFF'
         }), {
           "X-Parse-Application-Id": "12345",
           "Content-Type": "application/json"
         })
    result = json.loads(connection.getresponse().read())
    print (result)
    
    connection.request('PUT', '/parse/classes/Buses/'+obid[3]+'', json.dumps({
           "active_status": 'driverOFF'
         }), {
           "X-Parse-Application-Id": "12345",
           "Content-Type": "application/json"
         })
    result = json.loads(connection.getresponse().read())
    print (result)
    
    connection.request('PUT', '/parse/classes/Buses/'+obid[4]+'', json.dumps({
           "active_status": 'driverOFF'
         }), {
           "X-Parse-Application-Id": "12345",
           "Content-Type": "application/json"
         })
    result = json.loads(connection.getresponse().read())
    print (result)
    
    connection.request('PUT', '/parse/classes/Buses/'+obid[5]+'', json.dumps({
           "active_status": 'driverOFF'
         }), {
           "X-Parse-Application-Id": "12345",
           "Content-Type": "application/json"
         })
    result = json.loads(connection.getresponse().read())
    print (result)
    
    connection.request('PUT', '/parse/classes/Buses/'+obid[6]+'', json.dumps({
           "active_status": 'driverOFF'
         }), {
           "X-Parse-Application-Id": "12345",
           "Content-Type": "application/json"
         })
    result = json.loads(connection.getresponse().read())
    print (result)
    
    connection.request('PUT', '/parse/classes/Buses/'+obid[7]+'', json.dumps({
           "active_status": 'driverOFF'
         }), {
           "X-Parse-Application-Id": "12345",
           "Content-Type": "application/json"
         })
    result = json.loads(connection.getresponse().read())
    print (result)
    time.sleep(3600)