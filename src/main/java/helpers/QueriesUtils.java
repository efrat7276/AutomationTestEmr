package helpers;

public class QueriesUtils {
    public static final String removePatient_from_tbl =
            "INSERT INTO cpoe.drugInstructionExecution(\n" +
            "\tcpoeInstructionID, tzantar_id, executionDateTime, shiftExecutionDate, timesID, nextTimeId ,\n" +
            "\tInstructionExecutedID , executedInstructionGivenDose, drugUnitMeasureID ,\n" +
            "\tinstructionExecutionCommentsID , batchNumber, executedInstructionComment, ins_user, ins_date\n" +
            ")\n" +
            "VALUES(\n" +
            "\t3835699 ,\t\t\t\t\t\t\t\t-- cpoeInstructionID - bigint\n" +
            "\tNULL ,\t\t\t\t\t\t\t\t\t-- tzantar_id - int\n" +
            "\t'2025-12-14 10:50:42' ,\t\t\t\t\t\t-- executionDateTime - smalldatetime\n" +
            "\tNULL ,\t\t\t\t\t\t\t\t\t-- shiftExecutionDate - date\n" +
            "\t12 ,\t\t\t\t\t\t\t\t\t\t-- timesID - tinyint\n" +
            "\tNULL ,\t\t\t\t\t\t\t\t\t-- nextTimeId - tinyint\n" +
            "\tDEFAULT ,\t\t\t\t\t\t\t\t-- InstructionExecutedID - tinyint\n" +
            "\tNULL ,\t\t\t\t\t\t\t\t\t-- executedInstructionGivenDose - real\n" +
            "\tNULL ,\t\t\t\t\t\t\t\t\t-- drugUnitMeasureID - tinyint\n" +
            "\tNULL ,\t\t\t\t\t\t\t\t\t-- instructionExecutionCommentsID - smallint\n" +
            "\tNULL ,\t\t\t\t\t\t\t\t\t-- batchNumber - varchar(20)\n" +
            "\tNULL ,\t\t\t\t\t\t\t\t\t-- executedInstructionComment - varchar(500)\n" +
            "\t0 ,\t\t\t\t\t\t\t\t\t\t-- ins_user - int\n" +
            "\t'2025-12-14 10:50:42'\t-- ins_date - smalldatetime\n" +
            ")";
}
