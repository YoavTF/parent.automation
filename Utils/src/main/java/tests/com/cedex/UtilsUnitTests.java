package tests.com.cedex;

import com.cedex.text.RegExpr;
import com.cedex.text.textformat.TextBlockFormatter;
import junit.framework.SystemTestCase4;
import org.junit.Test;

public class UtilsUnitTests extends SystemTestCase4 {

    @Test
    public void firstTest() throws Exception {
        RegExpr regExpr =new RegExpr("found json1 id is: #{jsonValue1}","#\\{([\\w|_|\\d]+)\\}");
        if (regExpr.isFound()) {
            report.report("ok= "+regExpr.getGroupText(2));
        }
        System.out.print("not");
    }
    @Test
    public void print() {
        String kk="BB: Send GET request_12";
        kk=kk.replaceAll("_[\\d]+$","" );
        TextBlockFormatter.printHeader("BB: Validate Response Json by Xpath: result.id expected to: #{jsonValue100}", '-',false);
        //NOT GOOD FOR LONG DYNAMIC Strings
//        List<String> headersList = Arrays.asList("NAME", "GENDER", "MARRIED", "AGE", "SALARY($)");
//        List<List<String>> rowsList = Arrays.asList(
//                Arrays.asList("Eddy", "Male", "No", "23", "1200.27"),
//                Arrays.asList("Libby", "Male", "No", "17", "800.50"),
//                Arrays.asList("Rea", "Female", "No", "30", "10000.00"),
//                Arrays.asList("Deandre", "Female", "No", "19", "18000.50"),
//                Arrays.asList("Alice", "Male", "Yes", "29", "580.40"),
//                Arrays.asList("Alyse", "Female", "No", "26", "7000.89"),
//                Arrays.asList("Venessa", "Female", "No", "22", "100700.50")
//        );
//
//        Board board = new Board(75);
//        Table table = new Table(board, 75, headersList, rowsList);
//        table.setGridMode(Table.GRID_COLUMN);
//        //setting width and data-align of columns
//        List<Integer> colWidthsList = Arrays.asList(14, 14, 13, 14, 14);
//        List<Integer> colAlignList = Arrays.asList(Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER);
//        table.setColWidthsList(colWidthsList);
//        table.setColAlignsList(colAlignList);
//
//        Block tableBlock = table.tableToBlocks();
//        board.setInitialBlock(tableBlock);
//        board.build();
//        String tableString = board.getPreview();
//        System.out.println(tableString);
    }
}
