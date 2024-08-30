package eu.tutorials.myattendanceapp

import android.graphics.ImageFormat
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.platform.LocalContext
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QrCodeAnalyzer(private val onQrCodeScanned: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val supportedImageFormats =
        listOf(ImageFormat.YUV_420_888, ImageFormat.YUV_422_888, ImageFormat.YUV_444_888)

    override fun analyze(image: ImageProxy) {
        if (image.format in supportedImageFormats) {
            val bytes = image.planes.first().buffer.toByteArray()

            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )

            val binaryBmp = BinaryBitmap(HybridBinarizer(source))
            try {
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
                        )
                    )
                }.decode(binaryBmp)
                Log.d("QrCodeAnalyzer", result.text)
                onQrCodeScanned(result.text)
            } catch (e: Exception) {

                Log.d("QrCodeAnalyzer: ERROR!", "")
                // Don't do anything
            } finally {
                image.close()
            }
        }
    }


    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also { get(it) }
    }

}


/*
class QrCodeAnalyzer(
    private val onQrCodeScanned: (String) -> Unit
): ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
    )

    override fun analyze(image: ImageProxy) {
        if(image.format in supportedImageFormats) {
            Log.i("Credentials/QrCodeAnalyzer", image.format.toString())
            val bytes = image.planes.first().buffer.toByteArray()
            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )
            val binaryBmp = BinaryBitmap(HybridBinarizer(source))
            try {
                Log.i("Credentials/QrCodeAnalyzer", image.format.toString())
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE
                            )
                        )
                    )
                }.decode(binaryBmp)
                //val result = MultiFormatReader().decode(binaryBmp)
                Log.i("Credentials/QrCodeAnalyzer2", result.text)
                onQrCodeScanned(result.text)
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {
                image.close()
            }
        }
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }
}
*/
