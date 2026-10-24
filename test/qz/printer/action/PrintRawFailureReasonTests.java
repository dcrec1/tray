package qz.printer.action;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.standard.JobMessageFromOperator;
import javax.print.attribute.standard.PrinterMessageFromOperator;
import java.util.Locale;

public class PrintRawFailureReasonTests {
    @Test
    public void testUsesJobMessageBeforePrinterMessage() {
        HashPrintJobAttributeSet jobAttributes = new HashPrintJobAttributeSet();
        jobAttributes.add(new JobMessageFromOperator("Papel atolado", Locale.forLanguageTag("pt-BR")));
        HashPrintServiceAttributeSet printerAttributes = new HashPrintServiceAttributeSet();
        printerAttributes.add(new PrinterMessageFromOperator("Verifique a impressora", Locale.forLanguageTag("pt-BR")));

        Assert.assertEquals(PrintRaw.getPrintFailureReason(jobAttributes, printerAttributes), "Papel atolado");
    }

    @Test
    public void testUsesPrinterMessageWhenJobMessageIsMissing() {
        HashPrintServiceAttributeSet printerAttributes = new HashPrintServiceAttributeSet();
        printerAttributes.add(new PrinterMessageFromOperator("Verifique a impressora", Locale.forLanguageTag("pt-BR")));

        Assert.assertEquals(PrintRaw.getPrintFailureReason(new HashPrintJobAttributeSet(), printerAttributes), "Verifique a impressora");
    }

    @Test
    public void testReturnsNullWhenNoMessageIsAvailable() {
        Assert.assertNull(PrintRaw.getPrintFailureReason(new HashPrintJobAttributeSet(), new HashPrintServiceAttributeSet()));
    }
}
