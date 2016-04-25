SET-UP:
-------

To set up and run this project, we recommend you use IntelliJ. Other editors may work, but will require you to configure Maven and create build targets.

Open the StockGame/ folder as an IntelliJ project. Now make sure to synchronize the Maven dependencies.

=====
Testing:
=====

To run the tests, there is a test build target already created. We have 16 miscellaneous function tests in 
	test/java/StockLookupTest.java
	test/java/StockManagerTest.java
	test/java/UserTest.java

The four end-to-end tests are located in
	test/java/EndToEndTests.java

Note that these tests require a connection to the Internet, as our project uses an external API for stock data and CDNs for our stylesheets.

=====
Running:
=====

Once you've ensured the tests run on your machine, you should be able to run the program itself. There should be a build target for "Router.java". This is the main class for the program. Run this.

After a short spin-up, you can access the web server at localhost:4567 in your Internet browser. A test user is provided with the following credentials:
	Username: mfav
	Password: 123