08/15/24


1. already with firebase login
2. with clear button in registration form.
3. With toast message when field are blank in registration form.

08/16/24
4. with Add/ Insert method for new user.


08/17/24
5. Added loginuser table
6. with save functionality for scanned qrcode.


08/19/24
7. with latlong (save to db)
8. added back and register button in display generated qrcode.
9. With return string from function FetchLocation()


08/26/24
10. with working save image to picture directory in storage folder.

08/27/24
11. with exact address from provided lat and long..for sms advise sending.

08/28/24
12. With multiple sms phone number sending.
13. MBT logo
14. adjusted button size.


08/30/24
15. option to send notification via confirmation and dismiss button from alertDialog for SMS or Email.
16. Exit onDismissRequest
17. tested saving of logged-in.
18. test working email/sms notification
With Report Menu
19. Added Report menu in which it displays all the logged records in LoginUser table.
20. With delete button in row.

09/03/24
21. Report generator - PDF

09/04/24
22. Access limitations for non admin user.
- Disabled buttons.
23. Added val adminUser2 = "admin1234" for user limit.
24. adjust button size in SignupPage.
25. added script to clear text fields RegistrationMenu
26. Adjust spacing between salary details in ReportGenerator
27.
Added:
1. MyAppPreferences
- saveParameterValue
- getParameterValue
- to save last logged-in user to retain button limitation per user access.




09/06/24 >>> Enhancement 04
28. Added in Todo.kt
@ColumnInfo(defaultValue = "hh:mm:ss") var createdTime: String
- new column createdTime

29. added in build.gradle
        //to save the schema to directory
        //D:\Kiko Installers\github_projects\MyAttendanceProject\app\schemas\eu.tutorials.myattendanceapp.db.TodoDatabase
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
30. Added In TodoDatabase

//need for adding new column to table
@database(entities = [Todo::class, LoginUser::class], version = 2, exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)])
31. Arrange column in ReportGenerator.kt
 - added separate date and time logged column

32. Added new createdTime in reportMenu.kt
33. Added formatter in ScannedAlert.kt


09/09/24
Added:

34. TodoDatabase
 - new column createdTimeOut to compute time difference for Overtime...

35.  TodoDao
- Added update query function. to update emp timeout based on same date time/logged-in

36. TodoDatabase
- Update the version control to from :2 to :3

37. Added button LogMeMenuOut to support timeout.
38. Adjust report generator for the PDF report and include time difference
39. Added time difference and createdTimeOut  for display
40. Added ScannedAlert2 to support timeout UI
