import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun DateFormat(inputDateTime: String?): String {
    if (inputDateTime.isNullOrEmpty()) return ""

    return try {
        val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val outputFormatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM)
//            .ofLocalizedDateTime(FormatStyle.MEDIUM) //for datetime
            .withLocale(Locale.getDefault())

        val dateTime = OffsetDateTime.parse(inputDateTime, inputFormatter)
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
}
