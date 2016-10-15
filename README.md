# SoftUniFinalProject

<b>Budget Manager</b>

The goal of this project is to help people track their finances and have a closer look where their money are going and from where their incomings are bigger.

Budget Manager has Material Design UI which is easy and straight forward. At the main screen you have overall information about all the inputed income and all inputed expenses, your current balance, the amount of your entries and the last date you have input something.

The application also provide tab views for all inputed records based on their type. You can view every entry to access the full information it has. From the tabs with long click on entry you can delete the item. 

There is a dedicated Statistics screen where you can see the percantage of every category that you have inputed based on their amount. The statistics are presented in beautiful pie chart with the help of the external library MPAndroidChart.

Adding entries is prety easy. At the begining you choose to add income or expense from a expanding floating action buttons. The entry itself have two properties that have to be always inputed - the title and the amount of money. You can keep the default current date and category or change them from dialogs and spinner. Optionally you can give more details about the entry, add picture to remind you of the subject and add location. The user is notified with provided feedback if the record is saved into the data base or there is a error during the adding caused by missing information or something else.

You can start a background service which will trigger notificaton every 24 hours asking you to add new entries.

For keeping the entries in the application is using SQLite data base with the help of the external library Sugar ORM. There are three tables with two releations between them (one to many).

Thank you.
