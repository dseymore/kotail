#Why would anyone write a new way to interact with JMX.

JConsole is great. So is VisualVM. But, they have very limited capability to coordinate the monitoring of several user defined mbean attributes/operation values in one graph. The simple use case of 'monitoring all applications accessed through a load blaancer' pretty much lends itself to running a monitoring solution, but how does one take a quick snapshot of all values across many jvm instances.

If I want to coordinate the operation of a cache with load occurances.. and wait times of my daos.. can I get one graph? not without running a load test and exporting the values after the fact.

So, in a whole.. it's a convinience. Not a replacement for jconsole or VisualVM.. or a replacement for a monitoring solution. Its just a different kind of tool for a different situation.