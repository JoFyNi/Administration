# Administration

This is a Bot:

- taking information from CSV file (staus(n=Pending/ t=Approved/ f=Rejected), name, email, requestTag).
  - new Request start with Pending and get sorted by the status
- create a jFrame with true and false pane.
- list all information inside and updating the frame all 10sec.

Client Class:
- allows to create new entries for the csv file (create a new request)

Connector:
 - not Finish yet!
 - for interact via networks (local)
 
connection Interface:
 - for better communication between the classes
 
For what you can use it:
- Administration tool to handle installation request from employees in a local network
- gathering information for the Administration from employees.
- improve the code and make a Ticket system out of it.
- use it for monitoring a file (in this case a csv file)



Request Frame that lists all requests (n=Pending/ t=Approved/ f=Rejected):

![img.png](img.png)![img_1.png](img_1.png)![img_2.png](img_2.png)

notice: The listed employees are randomly generated and therefore not real
        The most of the path names are random generated Char's, just the last 10 results are real path generated with the user class(created requests)

Request Frame for the new request (Pending):

![img_3.png](img_3.png)

Console from frame with some info commands

![img_4.png](img_4.png)


Frame for a new Request:

![img_5.png](img_5.png)

