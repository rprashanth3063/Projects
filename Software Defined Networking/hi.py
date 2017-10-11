import subprocess
import requests
import json

while 1 == 1:
 cmd = raw_input('')
 if cmd == 'reset':
  resetcmd = 'curl http://127.0.0.1:8080/wm/acl/clear/json'
  subprocess.Popen(resetcmd, shell=True)
  #output = subprocess.check_output(['bash','-c', resetcmd])
  print ('all rules deleted')

 elif cmd == 'list':
  listcmd = 'curl http://127.0.0.1:8080/wm/acl/rules/json | python -mjson.tool'
  subprocess.Popen(listcmd, shell=True)
  #output = subprocess.check_output(['bash','-c', listcmd])
  print ('restricted')

 elif cmd == 'delete rule':
  uid = raw_input('')
  removecmd = 'curl -X DELETE -d \'{\"ruleid\":\"'+uid+'\"}\' http://127.0.0.1:8080/wm/acl/rules/json'
  subprocess.Popen(removecmd, shell=True)
  print ('rule deleted')
 


