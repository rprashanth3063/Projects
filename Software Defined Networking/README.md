
Mitigation of DoS/DDoS attack in SDN using Sflow-rt(Agent) by monitoring the OpenFlow switch. Whenever a DoS or DDoS attack happens. 

The firewall application will find out the IP of the attacker and will automatically push the flow rule to the controller to block to block theattack.

The communication between the controller and the system is through RestAPI. This restriction can be removed later using the same
application.

The threshold value can be put to classify whether the event is an attack or not, like- packets/sec.

This application can also be used as a load balancer.
