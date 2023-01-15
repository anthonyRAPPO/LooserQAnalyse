package apony.lol.LooserQAnalyse.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import apony.lol.LooserQAnalyse.service.interfaces.IUtilService;

@Service
public class IUtilServiceImpl implements IUtilService {

    @Override
    public List<String> convertResponseToListString(String res) {
        List<String> lstString = new ArrayList<>();
        String[] resTab = res.substring(1, res.length() - 1).split(",");
        for (String resString : resTab) {
            lstString.add(resString.replace("\"", ""));
        }
        return lstString;
    }

}
