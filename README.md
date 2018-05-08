## Java Academy Training – Final project
For the final test I had to create a small crawler that is able to extract all the products (tire products)
found in the website www.reifen.com, filter them only by vehicle type and ignore the rest of the types. From
the vehicle type, next step was to crawl all the dimensions of tires and save in a CSV file the brand, tire profile
and price.<br>

Using testNG and selenium libraries, I created a Test class in which I added a setup method, annotated
with @BeforeTest from testNG in order to be sure this method is called before any other method from my
Test class. In setup method I initialized the Chrome web driver and the File and FileWriter objects to use them
to write the CSV file.<br>

The next method I created was a test method; I annotated with @Test and added property priority 1
in order to be sure that this will be the first test method that gets called. In this method I extracted all the tire
dimensions and stored them into an array variable.<br>

After this I created a second test method annotated with @Test and priority 2 but beside that I also
added a dataProvider property which points to the array that I saved above. In this method I search for every
type of tire, receive returned result and iterate trough list of results in order to extract desired characteristics of
the tire and save them to CSV file. Because this method has a dataProvider property, it will be call repeatedly
until all elements from the array are finished.<br>

Lastly I created a finish method annotated with @AfterTest to be sure that this is the last method that
is called in my Test class. In this method I call close and quit methods of the selenium WebDriver object.