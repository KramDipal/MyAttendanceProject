package eu.tutorials.myattendanceapp


import android.graphics.Paint
import android.os.Environment
import android.graphics.pdf.PdfDocument
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter


fun writeDataToPdf(users: List<LoginUser>, filePath: String) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas


    var count: Int = 1
    var countDaysWork: Int = 0
    var countDaysPay: Double

    var paint: Paint = Paint() // Initialize with a non-null value
    paint.textSize = 10f
    paint.color = android.graphics.Color.BLACK

    //Biweekly Time Sheet
    var paintHeader: Paint = Paint() // Initialize with a non-null value
    paintHeader.textSize = 10f
    paintHeader.color = android.graphics.Color.GRAY

    //MACAU BUBBLE TEA SHOP
    var paintHeader2: Paint = Paint() // Initialize with a non-null value
    paintHeader2.textSize = 30f
    paintHeader2.color = android.graphics.Color.GREEN


    //Salary details
    var paintHeader3: Paint = Paint() // Initialize with a non-null value
    paintHeader3.textSize = 10f
    paintHeader3.color = android.graphics.Color.BLUE


    var yPosition = 50
    canvas.drawText("Biweekly Time Sheet",400f, yPosition.toFloat(), paintHeader)
    yPosition += 30



    canvas.drawText("MACAU BUBBLE TEA SHOP",5f, yPosition.toFloat(), paintHeader2)
    yPosition += 30

    canvas.drawText("Street Address:         C. RAYMUNDO AVE., MAYBUNGA                                                         Pay period start date:",5f, yPosition.toFloat(), paint)
    yPosition += 10

    canvas.drawText("Address 2:                     HAMPTON GARDENS ARCADE NORTHWING                             Pay period end date:",5f, yPosition.toFloat(), paint)
    yPosition += 10

    canvas.drawText("City, ST  ZIP Code:    PASIG CITY, 1607",5f, yPosition.toFloat(), paint)
    yPosition += 10

    canvas.drawText("Manager:                         FERDILIZA A. LAPID",5f, yPosition.toFloat(), paint)
    yPosition += 30

    canvas.drawText("_______________________________________________________________________________________________________________",5f, yPosition.toFloat(), paint)
    yPosition += 20

    canvas.drawText("No.                        Date Logged                                                              Employee name            Employee ID                  Overtime Hour/s",5f, yPosition.toFloat(), paint)
    yPosition += 20

    for (LoginUser in users)
    {

        canvas.drawText("${count}\t                             ${LoginUser.createdAt}\t                 ${LoginUser.fname}\t                         ${LoginUser.empid}        ",5f, yPosition.toFloat(),
            paint)
            yPosition += 10

        count++
    }

    countDaysWork = count/2
    countDaysPay = (countDaysWork * 500).toDouble()

    yPosition += 15
    canvas.drawText("_______________________________________________________________________________________________________________",5f, yPosition.toFloat(), paint)

    yPosition += 30
    canvas.drawText("Total days worked: \t${countDaysWork}",5f, yPosition.toFloat(), paintHeader3)
    yPosition += 10
    canvas.drawText("Rate per day:  Php 500.00",5f, yPosition.toFloat(), paintHeader3)
    yPosition += 10
    canvas.drawText("Total pay:  Php \t${countDaysPay}",5f, yPosition.toFloat(), paintHeader3)
    yPosition += 50


    canvas.drawText("_______________________________________________________",5f, yPosition.toFloat(), paint)
    yPosition += 8
    canvas.drawText("Employee Signature                                 Date",5f, yPosition.toFloat(), paint)
    yPosition += 30

    canvas.drawText("_______________________________________________________",5f, yPosition.toFloat(), paint)
    yPosition += 8
    canvas.drawText("Manager Signature                                    Date",5f, yPosition.toFloat(), paint)
    yPosition += 30


    pdfDocument.finishPage(page)

    val file = File(filePath)
    pdfDocument.writeTo(FileOutputStream(file))
    pdfDocument.close()
}


fun writeDataToCsv(User: List<LoginUser>, filePath: String) {
    val writer = CSVWriter(FileWriter(filePath))
    val header = arrayOf("MACAU BUBBLE TEA SHOP")
    writer.writeNext(header)


    val header1 = arrayOf("Street Address:", "C. RAYMUNDO AVE., MAYBUNGA", "", "", "", "Pay period start date:", "9/1/2024")
    writer.writeNext(header1)

    val header2 = arrayOf("Address 2:", "HAMPTON GARDENS ARCADE NORTHWING", "", "", "", "Pay period end date:", "9/15/2024")
    writer.writeNext(header2)

    val header3 = arrayOf("City, ST  ZIP Code:", "PASIG CITY")
    writer.writeNext(header3)

    val header4 = arrayOf("Employee:","LORIGO, APPLE JEINE PANGANIBAN","", "", "", "Employee phone:")
    writer.writeNext(header4)

    val header5 = arrayOf("Manager:","FERDILIZA A. LAPID", "", "", "", "Employee e-mail:")
    writer.writeNext(header5)

    val header6 = arrayOf(" ")
    writer.writeNext(header6)

    val header7 = arrayOf("Day", "Date", "Time-in", "Time-Out", "Overtime Hours", "Sick", "Vacation", "Total")
    writer.writeNext(header7)

    for (LoginUser in User) {
        val data = arrayOf(LoginUser.createdAt, LoginUser.fname, LoginUser.lname)

        writer.writeNext(data)
    }

    writer.close()
}