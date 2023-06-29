package pt.ipp.isep.dei.esoft.project.ui.gui.us18;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.ui.gui.us017.ListDealFile;


import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The type Analyse deal controller.
 */
public class AnalyseDealController {
    /**
     * The Import business controller.
     */
    ListDealFile importBusinessController = new ListDealFile();

    /**
     * Instantiates a new Analyse deal controller.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public AnalyseDealController() throws FileNotFoundException {
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        sa();
    }

    /**
     * Sa.
     *
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public static void sa() throws FileNotFoundException, ParseException {


        ListDealFile importBusinessController = new ListDealFile();
        List<Announcement> announcements = importBusinessController.transforminObject(Utils.readNonNullLineFromConsole("Choose a file"));

        // Filter and extract data for apartments and houses
        List<Double> dependentVariable = new ArrayList<>();

        for (Announcement announcement : announcements) {
            Property property = announcement.getRequest().getProperty();
            if (property instanceof Apartment) {
                dependentVariable.add(announcement.getOrders().get(0).getPrice());
            } else if (property instanceof House) {
                dependentVariable.add(announcement.getOrders().get(0).getPrice());
            }
        }

        double[][] x = new double[announcements.size()][5];
        for (Announcement annoucement : announcements) {
            if (annoucement.getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("APARTMENT")) {
                Apartment apartment = (Apartment) annoucement.getRequest().getProperty();
                x[announcements.indexOf(annoucement)][0] = apartment.getArea();
                x[announcements.indexOf(annoucement)][1] = apartment.getDistanceFromCityCentre();
                x[announcements.indexOf(annoucement)][2] = apartment.getNumberOfBedrooms();
                x[announcements.indexOf(annoucement)][3] = apartment.getNumberOfBathrooms();
                x[announcements.indexOf(annoucement)][4] = apartment.getNumberOfParkingSpaces();
            }
            if (annoucement.getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("HOUSE")) {
                House house = (House) annoucement.getRequest().getProperty();
                x[announcements.indexOf(annoucement)][0] = house.getArea();
                x[announcements.indexOf(annoucement)][1] = house.getDistanceFromCityCentre();
                x[announcements.indexOf(annoucement)][2] = house.getNumberOfBedrooms();
                x[announcements.indexOf(annoucement)][3] = house.getNumberOfBathrooms();
                x[announcements.indexOf(annoucement)][4] = house.getNumberOfParkingSpaces();

            }
        }

        double[] y = dependentVariable.stream().mapToDouble(Double::doubleValue).toArray();


        // Cria um objeto scanner para entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Solicita ao usuário que escolha o modelo de regressão
        System.out.println("Por favor, escolha o modelo de regressão:");
        System.out.println("1. Regressão Linear Simples");
        System.out.println("2. Regressão Linear Múltipla\n");
        int escolha = scanner.nextInt();
        int variavelI=0;
        if (escolha == 1) {
            System.out.println("Choose a Variable:\n0-Area;\n1-Distance form city;\n2-number of bedroomd;\n3-number of bathrooms;\n4-number of parking spaces");
             variavelI = scanner.nextInt();
        }
        System.out.println("Choose confidence level (1-100):(ex:95)");
        double nivelConfianca = (double) scanner.nextInt();


        int n = announcements.size();
        // Nível de confiança (95%)
        double grauDeconfianca = nivelConfianca/100;
        // Calculando o intervalo de confiança do intercepto
        TDistribution tistribution = new TDistribution(n - 2);
        double valorCritico = tistribution.inverseCumulativeProbability(1 - (1 - grauDeconfianca) / 2);

        // Realiza o modelo de regressão selecionado
        if (escolha == 1) {
            // Simple Linear Regression
            SimpleRegression slr = new SimpleRegression();
            for (int i = 0; i < x.length; i++) {
                slr.addData(x[i][variavelI], y[i]);
//                slr.addData(x[i][0], y[i]);

            }
            double slrIntercept = slr.getIntercept();
            double slrSlope = slr.getSlope();
            double slrRSquared = slr.getRSquare();
            double slrR = Math.sqrt(slrRSquared);
            // Calculate standard errors and confidence intervals
            double slrInterceptStdErr = slr.getInterceptStdErr();
            double slrSlopeStdErr = slr.getSlopeStdErr();

            // Display the results
            System.out.println("Simple Linear Regression Model:\n");
            System.out.println("Intercepto(ordenada na origem): " + slrIntercept+"\n");
            System.out.println("Declive: " + slrSlope+"\n");
            System.out.println("R-squared: " + slrRSquared+"\n");
            System.out.println("R: " + slrR+"\n");
            System.out.println("\nIntercept Standard Error: " + slrInterceptStdErr+"\n");
            System.out.println("declive Standard Error: " + slrSlopeStdErr+"\n");
            System.out.println("Valor critico: "+ valorCritico+"\n");


            if (variavelI==0){
                double xMediavariable = 0;
                for (Announcement annoucement : announcements) {
                    xMediavariable += annoucement.getRequest().getProperty().getArea();
                }
                xMediavariable = xMediavariable / announcements.size();

                double[] yChapeu = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {
                    yChapeu[i] = slr.predict(announcements.get(i).getRequest().getProperty().getArea());
                }

                double[] yMenosYChapeuQuadrado = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {
                    yMenosYChapeuQuadrado[i] = Math.pow(announcements.get(i).getOrders().get(0).getPrice() - yChapeu[i], 2);
                }

                double sVariable = 0;
                for (int i = 0; i < announcements.size(); i++) {
                    sVariable += yMenosYChapeuQuadrado[i];
                }
                sVariable = Math.sqrt(sVariable / (announcements.size() - 2));

                //intercepto
                double slrInterceptConfidenceIntervalLower =slr.getIntercept() - valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));
                double slrInterceptConfidenceIntervalUpper = slr.getIntercept() + valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));


                //declive
                double slrdecliveConfidenceIntervalLower = slr.getSlope() - valorCritico * sVariable * Math.sqrt(1 / slr.getXSumSquares());
                double slrdecliveConfidenceIntervalUpper = slr.getSlope() + valorCritico * sVariable * Math.sqrt(1 / slr.getXSumSquares());


                System.out.println("(a^)Intercept Confidence Interval: [" + slrInterceptConfidenceIntervalLower + ", " + slrInterceptConfidenceIntervalUpper + "]"+"\n");
                System.out.println("(b^)Slope Confidence Interval: [" + slrdecliveConfidenceIntervalLower + ", " + slrdecliveConfidenceIntervalUpper + "]"+"\n");

                printHypothesisTestsSimple(nivelConfianca, slr, xMediavariable, announcements, sVariable);
            }
            if (variavelI==1){
                double xMediavariable = 0;
                for (Announcement annoucement : announcements) {
                    xMediavariable += annoucement.getRequest().getProperty().getDistanceFromCityCentre();
                }
                xMediavariable = xMediavariable / announcements.size();

                double[] yChapeu = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {
                    yChapeu[i] = slr.predict(announcements.get(i).getRequest().getProperty().getDistanceFromCityCentre());
                }

                double[] yMenosYChapeuQuadrado = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {
                    yMenosYChapeuQuadrado[i] = Math.pow(announcements.get(i).getOrders().get(0).getPrice() - yChapeu[i], 2);
                }

                double sVariable = 0;
                for (int i = 0; i < announcements.size(); i++) {
                    sVariable += yMenosYChapeuQuadrado[i];
                }
                sVariable = Math.sqrt(sVariable / (announcements.size() - 2));

                //intercepto
                double slrInterceptConfidenceIntervalLower =slr.getIntercept() - valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));
                double slrInterceptConfidenceIntervalUpper = slr.getIntercept() + valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));


                //declive
                double slrdecliveConfidenceIntervalLower = slr.getSlope() - slr.getSlopeConfidenceInterval();
                double slrdecliveConfidenceIntervalUpper = slr.getSlope() + slr.getSlopeConfidenceInterval();


                System.out.println("(a^)Intercept Confidence Interval: [" + slrInterceptConfidenceIntervalLower + ", " + slrInterceptConfidenceIntervalUpper + "]"+"\n");
                System.out.println("(b^)Slope Confidence Interval: [" + slrdecliveConfidenceIntervalLower + ", " + slrdecliveConfidenceIntervalUpper + "]"+"\n");

                printHypothesisTestsSimple(nivelConfianca, slr, xMediavariable, announcements, sVariable);
            }
            if (variavelI==2){
                double xMediavariable = 0;
                for (Announcement annoucement : announcements) {
                    if (annoucement.getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("APARTMENT")) {
                        Apartment apartment = (Apartment) annoucement.getRequest().getProperty();
                        xMediavariable += apartment.getNumberOfBedrooms();

                    }
                    if (annoucement.getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("HOUSE")) {
                        House house = (House) annoucement.getRequest().getProperty();
                        xMediavariable += house.getNumberOfBedrooms();
                    }
                }
                xMediavariable = xMediavariable / announcements.size();

                double[] yChapeu = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {
                    if (announcements.get(i).getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("APARTMENT")) {
                        Apartment apartment = (Apartment) announcements.get(i).getRequest().getProperty();
                        yChapeu[i] += slr.predict(apartment.getNumberOfBedrooms());

                    }
                    if (announcements.get(i).getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("HOUSE")) {
                        House house = (House) announcements.get(i).getRequest().getProperty();
                        yChapeu[i] += slr.predict(house.getNumberOfBedrooms());
                    }

                }

                double[] yMenosYChapeuQuadrado = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {
                    yMenosYChapeuQuadrado[i] = Math.pow(announcements.get(i).getOrders().get(0).getPrice() - yChapeu[i], 2);
                }

                double sVariable = 0;
                for (int i = 0; i < announcements.size(); i++) {
                    sVariable += yMenosYChapeuQuadrado[i];
                }
                sVariable = Math.sqrt(sVariable / (announcements.size() - 2));

                //intercepto
                double slrInterceptConfidenceIntervalLower =slr.getIntercept() - valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));
                double slrInterceptConfidenceIntervalUpper = slr.getIntercept() + valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));


                //declive
                double slrdecliveConfidenceIntervalLower = slr.getSlope() - slr.getSlopeConfidenceInterval();
                double slrdecliveConfidenceIntervalUpper = slr.getSlope() + slr.getSlopeConfidenceInterval();


                System.out.println("(a^)Intercept Confidence Interval: [" + slrInterceptConfidenceIntervalLower + ", " + slrInterceptConfidenceIntervalUpper + "]"+"\n");
                System.out.println("(b^)Slope Confidence Interval: [" + slrdecliveConfidenceIntervalLower + ", " + slrdecliveConfidenceIntervalUpper + "]"+"\n");

                printHypothesisTestsSimple(nivelConfianca, slr, xMediavariable, announcements, sVariable);
            }
            if (variavelI==3){
                double xMediavariable = 0;
                for (Announcement annoucement : announcements) {
                    if (annoucement.getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("APARTMENT")) {
                        Apartment apartment = (Apartment) annoucement.getRequest().getProperty();
                        xMediavariable += apartment.getNumberOfBathrooms();

                    }
                    if (annoucement.getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("HOUSE")) {
                        House house = (House) annoucement.getRequest().getProperty();
                        xMediavariable += house.getNumberOfBathrooms();
                    }
                }
                xMediavariable = xMediavariable / announcements.size();

                double[] yChapeu = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {

                    if (announcements.get(i).getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("APARTMENT")) {
                        Apartment apartment = (Apartment) announcements.get(i).getRequest().getProperty();
                        yChapeu[i] += slr.predict(apartment.getNumberOfBathrooms());

                    }
                    if (announcements.get(i).getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("HOUSE")) {
                        House house = (House) announcements.get(i).getRequest().getProperty();
                        yChapeu[i] += slr.predict(house.getNumberOfBathrooms());
                    }

                }

                double[] yMenosYChapeuQuadrado = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {
                    yMenosYChapeuQuadrado[i] = Math.pow(announcements.get(i).getOrders().get(0).getPrice() - yChapeu[i], 2);
                }

                double sVariable = 0;
                for (int i = 0; i < announcements.size(); i++) {
                    sVariable += yMenosYChapeuQuadrado[i];
                }

                sVariable = Math.sqrt(sVariable / (announcements.size() - 2));

                //intercepto
                double slrInterceptConfidenceIntervalLower =slr.getIntercept() - valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));
                double slrInterceptConfidenceIntervalUpper = slr.getIntercept() + valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));


                //declive
                double slrdecliveConfidenceIntervalLower = slr.getSlope() - slr.getSlopeConfidenceInterval();
                double slrdecliveConfidenceIntervalUpper = slr.getSlope() + slr.getSlopeConfidenceInterval();


                System.out.println("(a^)Intercept Confidence Interval: [" + slrInterceptConfidenceIntervalLower + ", " + slrInterceptConfidenceIntervalUpper + "]"+"\n");
                System.out.println("(b^)Slope Confidence Interval: [" + slrdecliveConfidenceIntervalLower + ", " + slrdecliveConfidenceIntervalUpper + "]"+"\n");

                printHypothesisTestsSimple(nivelConfianca, slr, xMediavariable, announcements, sVariable);
            }
            if (variavelI==4){
                double xMediavariable = 0;
                for (Announcement annoucement : announcements) {
                    if (annoucement.getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("APARTMENT")) {
                        Apartment apartment = (Apartment) annoucement.getRequest().getProperty();
                        xMediavariable += apartment.getNumberOfParkingSpaces();

                    }
                    if (annoucement.getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("HOUSE")) {
                        House house = (House) annoucement.getRequest().getProperty();
                        xMediavariable += house.getNumberOfParkingSpaces();
                    }
                }
                xMediavariable = xMediavariable / announcements.size();

                double[] yChapeu = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {

                    if (announcements.get(i).getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("APARTMENT")) {
                        Apartment apartment = (Apartment) announcements.get(i).getRequest().getProperty();
                        yChapeu[i] += slr.predict(apartment.getNumberOfParkingSpaces());

                    }
                    if (announcements.get(i).getRequest().getProperty().getTypeOfProperty().toString().equalsIgnoreCase("HOUSE")) {
                        House house = (House) announcements.get(i).getRequest().getProperty();
                        yChapeu[i] += slr.predict(house.getNumberOfParkingSpaces());
                    }

                }

                double[] yMenosYChapeuQuadrado = new double[announcements.size()];
                for (int i = 0; i < announcements.size(); i++) {
                    yMenosYChapeuQuadrado[i] = Math.pow(announcements.get(i).getOrders().get(0).getPrice() - yChapeu[i], 2);
                }

                double sVariable = 0;
                for (int i = 0; i < announcements.size(); i++) {
                    sVariable += yMenosYChapeuQuadrado[i];
                }

                sVariable = Math.sqrt(sVariable / (announcements.size() - 2));

                //intercepto
                double slrInterceptConfidenceIntervalLower =slr.getIntercept() - valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));
                double slrInterceptConfidenceIntervalUpper = slr.getIntercept() + valorCritico * sVariable * Math.sqrt((1 / (double) announcements.size()) + (Math.pow(xMediavariable, 2) / slr.getXSumSquares()));


                //declive
                double slrdecliveConfidenceIntervalLower = slr.getSlope() - slr.getSlopeConfidenceInterval();
                double slrdecliveConfidenceIntervalUpper = slr.getSlope() + slr.getSlopeConfidenceInterval();


                System.out.println("(a^)Intercept Confidence Interval: [" + slrInterceptConfidenceIntervalLower + ", " + slrInterceptConfidenceIntervalUpper + "]"+"\n");
                System.out.println("(b^)Slope Confidence Interval: [" + slrdecliveConfidenceIntervalLower + ", " + slrdecliveConfidenceIntervalUpper + "]"+"\n");

                printHypothesisTestsSimple(nivelConfianca, slr, xMediavariable, announcements, sVariable);
            }



        } else if (escolha == 2) {
            // Multiple Linear Regression
            OLSMultipleLinearRegression mlr = new OLSMultipleLinearRegression();
            mlr.newSampleData(y, x);
            double[] mlrCoefficients = mlr.estimateRegressionParameters();
            double mlrRSquared = mlr.calculateRSquared();
            double mlrAdjustedRSquared = mlr.calculateAdjustedRSquared(); // Display the results
            System.out.println("regressão multilinear");
            System.out.println("Coeficientes:");
            for (int i = 0; i < mlrCoefficients.length; i++) {
                System.out.println("Variable " + i + ": " + mlrCoefficients[i]);
            }
            System.out.println("\n" + "R-squared: " + mlrRSquared + "\n\n");
            System.out.println("Adjusted R-squared: " + mlrAdjustedRSquared + "\n\n");


            double[][] matrixdata = new double[announcements.size()][6]; // Alterado para 5, pois não há necessidade de armazenar o valor 1 na primeira coluna
            for (int i = 0; i < announcements.size(); i++) {
                Announcement annoucement = announcements.get(i);
                matrixdata[i][0] = 1; // Configura o valor padrão para 1 na primeira coluna

                Property property = annoucement.getRequest().getProperty();
                if (property instanceof Apartment) {
                    Apartment apartment = (Apartment) property;
                    matrixdata[i][1] = apartment.getArea();
                    matrixdata[i][2] = apartment.getDistanceFromCityCentre();
                    matrixdata[i][3] = apartment.getNumberOfBedrooms();
                    matrixdata[i][4] = apartment.getNumberOfBathrooms();
                    matrixdata[i][5] = apartment.getNumberOfParkingSpaces();
                } else if (property instanceof House) {
                    House house = (House) property;
                    matrixdata[i][1] = house.getArea();
                    matrixdata[i][2] = house.getDistanceFromCityCentre();
                    matrixdata[i][3] = house.getNumberOfBedrooms();
                    matrixdata[i][4] = house.getNumberOfBathrooms();
                    matrixdata[i][5] = house.getNumberOfParkingSpaces();
                }
            }

            RealMatrix matrix = MatrixUtils.createRealMatrix(matrixdata);
            RealMatrix matrixT = matrix.transpose();
            RealMatrix matrixMult = matrixT.multiply(matrix);
            RealMatrix matrixInverse = MatrixUtils.inverse(matrixMult);
            double[][] Cjj = matrixInverse.getData();

            double sSquared = mlr.calculateResidualSumOfSquares() / (announcements.size() - 6);

            ConfidenceIntervalMultiple(mlr, nivelConfianca, announcements, sSquared, Cjj);
            printHypithesisTestsMultiple(nivelConfianca, mlr, announcements, sSquared, Cjj);

            System.out.println("Intervalo de Confiança (" + (grauDeconfianca * 100) + "%):\nValor Critico:" + valorCritico + "\n\n");



        } else {
            System.out.println("Invalid choice. Please choose 1 or 2.");
        }


    }

    /**
     * Print hypothesis tests simple.
     *
     * @param confidenceLevel  the confidence level
     * @param simpleRegression the simple regression
     * @param xMedia           the x media
     * @param announcements    the announcements
     * @param s                the s
     */
    public static void printHypothesisTestsSimple(double confidenceLevel, SimpleRegression simpleRegression, double xMedia, List<Announcement> announcements, double s) {
        //bilateral
        int n = announcements.size();
        // Nível de confiança (95%)
        double grauDeconfianca = confidenceLevel/100;

        TDistribution tistribution = new TDistribution(n - 2);
        double valorCritico = tistribution.inverseCumulativeProbability(1 - (1 - grauDeconfianca) / 2);


        double ta = simpleRegression.getIntercept() / (s * Math.sqrt((1 / announcements.size()) + (Math.pow(xMedia, 2) / simpleRegression.getXSumSquares())));
        System.out.println("\nTa = " + ta);
        System.out.println("For a^:");
        System.out.println("H0: a^ = 0    vs    H1: a^ != 0");
        if (Math.abs(ta) > valorCritico) {
            System.out.println("|Ta| > t\n So H0 is rejected");
        } else {
            System.out.println("|Ta| isn't bigger than t\n So H0 is not rejected");
        }
        double tb = simpleRegression.getSlope() / (s * Math.sqrt(1 / simpleRegression.getXSumSquares()));
        System.out.println("\nTb = " + tb);
        System.out.println("For b^:");
        System.out.println("H0: b^ = 0    vs    H1: b^ != 0");
        if (Math.abs(tb) > valorCritico) {
            System.out.println("|Tb| > t\n So H0 is rejected");
        } else {
            System.out.println("|Tb| isn't bigger than t\n So H0 is not rejected");
        }
    }

    /**
     * Confidence interval multiple.
     *
     * @param regression      the regression
     * @param confidenceLevel the confidence level
     * @param announcements   the announcements
     * @param sSquared        the s squared
     * @param Cjj             the cjj
     */
    public static void ConfidenceIntervalMultiple(OLSMultipleLinearRegression regression, double confidenceLevel, List<Announcement> announcements, double sSquared, double[][] Cjj) {
        double limiteInferior;
        double limiteSuperior;
        int n = announcements.size();
        // Nível de confiança (95%)
        double grauDeconfianca = confidenceLevel/100;

        TDistribution tistribution = new TDistribution(n - 2);
        double valorCritico = tistribution.inverseCumulativeProbability(1 - (1 - grauDeconfianca) / 2);

        for (int i = 0; i <= 5; i++) {
            String beta = "Beta" + i;
            limiteInferior = regression.estimateRegressionParameters()[i] - valorCritico * Math.sqrt(sSquared * Cjj[i][i]);
            limiteSuperior = regression.estimateRegressionParameters()[i] + valorCritico * Math.sqrt(sSquared * Cjj[i][i]);
            System.out.printf("Confidence Interval for %s: ]%f ; %f[", beta, limiteInferior, limiteSuperior);
            System.out.println();
        }

    }

    /**
     * Print hypithesis tests multiple.
     *
     * @param confidenceLevel the confidence level
     * @param regression      the regression
     * @param announcements   the announcements
     * @param sSquared        the s squared
     * @param Cjj             the cjj
     */
    public static void printHypithesisTestsMultiple(double confidenceLevel, OLSMultipleLinearRegression regression, List<Announcement> announcements, double sSquared, double[][] Cjj) {
        int n = announcements.size();
        // Nível de confiança (95%)
        double grauDeconfianca = confidenceLevel/100;

        TDistribution tistribution = new TDistribution(n - 2);
        double valorCritico = tistribution.inverseCumulativeProbability(1 - (1 - grauDeconfianca) / 2);


        double[] tBeta = new double[regression.estimateRegressionParameters().length];
        for (int i = 0; i < regression.estimateRegressionParameters().length; i++) {
            tBeta[i] = regression.estimateRegressionParameters()[i] / Math.sqrt(sSquared * Cjj[i][i]);
        }

        System.out.println("\nHypothesis Tests:\n");
        System.out.println("H0: Beta" + 0 + " = 0    vs    H1: Beta" + 0 + " != 0");
        System.out.println("Ta = " + tBeta[0]);
        if (Math.abs(tBeta[0]) > valorCritico) {
            System.out.println("|Ta| > t\n So H0 is rejected");
        } else {
            System.out.println("|Ta| isn't bigger than t\n So H0 is not rejected");
        }
        System.out.println();


        for (int i = 0; i < 6; i++) {
            if (i!=0){
                System.out.println("H0: Beta" + i + " = 0    vs    H1: Beta" + i + " != 0");
                System.out.println("Tb = " + tBeta[i]);
                if (Math.abs(tBeta[i]) > valorCritico) {
                    System.out.println("|Tb| > t\n So H0 is rejected");
                } else {
                    System.out.println("|Tb| isn't bigger than t\n So H0 is not rejected");
                }
                System.out.println();
            }
        }
    }
}



