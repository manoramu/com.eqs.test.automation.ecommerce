# EQS Automation Project

Please find below the steps to setup this project in eclipse and to run the project in the same

### Basic Requirements
- Java 1.8 or greater
- eclipse ide with maven

### Project Setup

  1. Clone this repository in your local machine
  2. Open eclipse and import this project into your eclipse workspace as below
        1. File Menu > Import > Projects from Folder or Archive
        2. Click on Directory button in Import wizard and select the path where you have cloned this repository
        3. Click on Finish to import the repository as project in eclipse
  3. Either right click on the project and select **Maven > Update project...** or go to command prompt to this project location and type `mvn clean install`
  4. Right click on the project in eclipse and select **Properties** where you will land up in Properties window in eclipse
        1. Click on **Add Library** button which launches Add Library Window
        2. Select **TestNG** in the options provided 
        3. Click on Next and then Finish
        4. Back in Properties window, click on **Apply and Close** button
  
### Run Project

1. Open testng.xml file in eclipse
2. Right click and select **Run As > TestNG Suite**

You will be able to see the project initiating 3 drivers - viz Chrome Driver, IE Driver and Mozilla Driver parallely

### Reports

Once the project is run, you will be able to see the reports in the following location under workspace
`test-output > index.html`
Regarding the output for list of product names visible in the home page, in the report webpage find `Reporter Output` and you will be able to see the list of products as suggested
