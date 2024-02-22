
Advance of Funds Project - Administrative Circuit Explanation
This repository contains the backend code for the Advance of Funds project. Below is an overview of how the administrative circuit works and the features of the software.

Administrative Circuit Overview
State of Accounts by Service (Health Regions)
Expedients and resolutions from various Health Regions, including Hospitals, Health Centers, and Sanitary Posts, are loaded into the system. Each Health Region is divided into the following:

REGION SANITARIA I
REGION SANITARIA II
REGION SANITARIA III
REGION SANITARIA IV
REGION SANITARIA V
REGION SANITARIA VI
REGION SANITARIA VII
REGION SANITARIA VIII
SUBSECRETARY
C.I.C AND PRIVATE
All Hospitals, Health Centers, and Sanitary Posts from each Region and Subsecretary liquidate 60% of the amount of the expedient/resolution, while C.I.C and Private entities liquidate 100%.

Expense Control
Expenses are tracked using two main tables:

Expedient Movement Table
Total Amount by Resolutions Table
These tables provide a comprehensive overview of expenses and resolutions, ensuring accurate financial tracking.

Summary Report
A summary report is generated to ensure there are no discrepancies between expedients and resolutions. This report highlights any outstanding balances that need to be addressed.

Software Requirements
The software should include the following features:

User login with different access levels: administrator with full access and administrative user with read-only access to graphs and available balances.
Annual evolution graphs per effector and per region, showing the dates of advance fund requests and renditions.
Display of targets, indicating pending advance requests, renditions, and deadlines.
Exportable reports to PDF and Excel formats for easy sharing via email.
Repository Structure
The repository is organized as follows:

src/: Contains the source code for the RESTful APIs.
docs/: Documentation files, including README.md and any additional documentation.
tests/: Unit tests for the APIs.
config/: Configuration files for the database and other settings.
Getting Started
To get started with the project, follow these steps:

Clone the repository to your local machine.
Install the necessary dependencies using npm install.
Configure the database and other settings in the config/ directory.
Run the application using npm start.
Access the APIs using the provided endpoints.
Contributors
Darian A. Hiebl
Federico Miranda
