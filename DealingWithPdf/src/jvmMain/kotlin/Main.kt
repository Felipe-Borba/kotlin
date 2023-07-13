import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lowagie.text.*
import java.io.FileOutputStream;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                text = "Hello, Desktop!"
            }) {
                Text(text)
            }

            Button(onClick = {
                val myPDFDoc = Document()
                val pdfWriter = PdfWriter.getInstance(myPDFDoc, FileOutputStream("./sample1.pdf"))


                // 1) Define a string
                val title = "Learning OpenPDF with Kotlin"

                // 2) Define a multi line string using | character for margin so it can be removed using trimMargin()
                val lorenIpsum1 = """Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo 
                |ligula eget dolor. Aenean massa. 
                |Cum sociis natoque penatibus et magnis dis
                |parturient montes, nascetur ridiculus mus. 
                    
                |Donec quam felis, ultricies nec, pellentesque eu, 
                |pretium quis, sem. Nulla consequat massa quis enim. 
                |Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.
                |In enim justo, rhoncus ut, imperdiet a, venenatis vitae, 
                |justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. 
                |Cras dapibus. Vivamus elementum semper nisi. 
                |Aenean vulputate eleifend tellus."""
                .trimMargin() // removed the margin on occurrences for the char "|"

                myPDFDoc.apply {
                    /* Here we add some metadata to the generated pdf */
                    addTitle("This is a simple PDF example")
                    addSubject("This is a tutorial explaining how to use openPDF")
                    addKeywords("Kotlin, OpenPDF, Basic sample")
                    addCreator("Miguel and Kesizo.com")
                    addAuthor("Miguel Doctor")

                    open()

                    val titleFont = Font(Font.COURIER, 20f, Font.BOLDITALIC, Color.BLUE)

                    add(
                        Paragraph(title,titleFont).apply {
                            // 4) Element class provides properties to align
                            // Content elements within the document
                            setAlignment(Element.ALIGN_CENTER)
                        })

                    // 5) Adding an empty line
                    add(Paragraph(Chunk.NEWLINE))

                    // 6) Include the text as content of the pdf
                    add(Paragraph(lorenIpsum1))
                    close()
                }
                pdfWriter.close() // close the File writer

                println("finished!")
            }) {
                Text("hello pdf")
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
