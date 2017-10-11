import requests
import json
import subprocess
#import threading
#from threading import thread


flows = {'keys':'ipsource,ipdestination','value':'bytes','filter':''}
threshold = {'metric':'flows','value':10000}

target = 'http://localhost:8008'
 
r = requests.put(target + '/flow/flows/json',data=json.dumps(flows))
r = requests.put(target + '/threshold/flows/json',data=json.dumps(threshold))

eventurl = target + '/events/json?maxEvents=10&timeout=6'
eventID = -1
while 1 == 1:
  r = requests.get(eventurl + '&eventID=' + str(eventID))
  if r.status_code != 200: break
  events = r.json()
  if len(events) == 0: continue

  eventID = events[0]["eventID"]
  result = []
  for i in events:
    if 'flows' == i['metric']:
      r = requests.get(target + '/metric/' + i['agent'] + '/' + i['metric'] + '/json')
      metric = r.json()
      if len(metric) > 0:
        #print metric[0]["topKeys"][0]["key"]
       result.append(metric[0]["topKeys"][0]["key"])
  splitIP = result[0].split(",")
  acl = 'curl -X POST -d \'{\"src-ip\":\"'+splitIP[0]+'/32\",\"dst-ip\":\"'+splitIP[1]+'/32\",\"action\":\"deny\"}\' http://127.0.0.1:8080/wm/acl/rules/json'
  subprocess.Popen(acl, shell=True, stdout=subprocess.PIPE)
  #output = subprocess.check_output(['bash','-c', acl])
  #print (output)
