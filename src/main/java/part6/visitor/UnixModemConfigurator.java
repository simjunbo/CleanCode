package part6.visitor;

/**
 * Created by simjunbo on 2018-04-19.
 */
public class UnixModemConfigurator implements ModemVisitor {
    public void visit(HayesModem modem) {
        modem.configurationString = "&s1=4&D=3";
    }

    public void visit(ZoomModem modem) {
        modem.configurationValue = 42;
    }

    public void visit(ErnieModem modem) {
        modem.internalPattern = "C is too slow";
    }
}
