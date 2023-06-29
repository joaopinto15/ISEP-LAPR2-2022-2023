package pt.ipp.isep.dei.esoft.project.ui.gui.us18;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.ui.gui.us017.ListDealFile;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The type Matcp.
 */
public class MATCP {
    /**
     * The Import business controller.
     */
    ListDealFile importBusinessController = new ListDealFile();

    /**
     * Instantiates a new Matcp.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public MATCP() throws FileNotFoundException {
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
        List<Announcement> announcements =importBusinessController.transforminObject("legacyRealStateUSAMoodle_MATCP_MDISC .csv");

        // Filter and extract data for apartments and houses
        List<double[]> independentVariables = new ArrayList<>();
        List<Double> dependentVariable = new ArrayList<>();

        for (Announcement announcement : announcements) {
            Property property = announcement.getRequest().getProperty();
            if (property instanceof Apartment) {
                Apartment apartment = (Apartment) property;
                double[] variables = {
                        property.getArea(),
                        apartment.getDistanceFromCityCentre(),
                        apartment.getNumberOfBedrooms(),
                        apartment.getNumberOfBathrooms(),
                        apartment.getNumberOfParkingSpaces()
                };
                independentVariables.add(variables);
                dependentVariable.add(announcement.getOrders().get(0).getPrice());
            } else if (property instanceof House) {
                House house = (House) property;
                double[] variables = {
                        property.getArea(),
                        house.getDistanceFromCityCentre(),
                        house.getNumberOfBedrooms(),
                        house.getNumberOfBathrooms(),
                        house.getNumberOfParkingSpaces()
                };
                independentVariables.add(variables);
                dependentVariable.add(announcement.getOrders().get(0).getPrice());
            }
        }

        double[][] x = independentVariables.toArray(new double[0][]);
        double[] y = dependentVariable.stream().mapToDouble(Double::doubleValue).toArray();


            // Cria um objeto scanner para entrada do usuário
            Scanner scanner = new Scanner(System.in);

            // Solicita ao usuário que escolha o modelo de regressão
            System.out.println("Por favor, escolha o modelo de regressão:");
            System.out.println("1. Regressão Linear Simples");
            System.out.println("2. Regressão Linear Múltipla");
            int escolha = scanner.nextInt();




            // Realiza o modelo de regressão selecionado
            if (escolha == 1) {
                // Simple Linear Regression
                SimpleRegression slr = new SimpleRegression();
                for (int i = 0; i < x.length; i++) {
                    slr.addData(x[i][0], y[i]);
                }
                double slrIntercept = slr.getIntercept();
                double slrSlope = slr.getSlope();
                double slrRSquared = slr.getRSquare();


                // Display the results
                System.out.println("Simple Linear Regression Model:");
                System.out.println("Coeficiente angular: " + slrIntercept);
                System.out.println("Declive: " + slrSlope);
                System.out.println("R-squared: " + slrRSquared);



//                // Calculate standard errors and confidence intervals
                double slrInterceptStdErr = slr.getInterceptStdErr();
                double slrSlopeStdErr = slr.getSlopeStdErr();
//                double slrInterceptConfidenceIntervalLower = slr.getInterceptConfidenceInterval();
//                double slrInterceptConfidenceIntervalUpper = slr.getInterceptConfidenceInterval() + slr.getIntercept();
                //declive
                double slrdecliveConfidenceIntervalLower = slr.getSlopeConfidenceInterval();
                double slrdecliveConfidenceIntervalUpper = slr.getSlopeConfidenceInterval() + slr.getSlope();

                System.out.println("\nIntercept Standard Error: " + slrInterceptStdErr);
                System.out.println("declive Standard Error: " + slrSlopeStdErr);
//                System.out.println("(a^)Intercept Confidence Interval: [" + slrInterceptConfidenceIntervalLower + ", " + slrInterceptConfidenceIntervalUpper + "]");
                System.out.println("(b^)Slope Confidence Interval: [" + slrdecliveConfidenceIntervalLower + ", " + slrdecliveConfidenceIntervalUpper + "]");

//                //teste hipoteses
                // Realiza o teste de hipótese para a inclinação da regressão
//                double[] residuals = slr.getResiduals();
//                TTest tTest = new TTest();
//                double slopePValue = tTest.tTest(slrSlope, residuals);
//
//                // Exibe o resultado do teste de hipótese
//                System.out.println("Teste de Hipótese (Inclinação da Regressão):");
//                System.out.println("p-value: " + slopePValue);
//                if (slopePValue < 0.05) {
//                    System.out.println("Rejeitar a hipótese nula");
//                } else {
//                    System.out.println("Aceitar a hipótese nula");
//                }


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
                System.out.println("\n"+"R-squared: " + mlrRSquared+"\n\n");
                System.out.println("Adjusted R-squared: " + mlrAdjustedRSquared+"\n\n");


//cofianla parametro
                double[] intervaloconfiança = mlr.estimateRegressionParametersStandardErrors();
                double[] limitesInferiores = new double[intervaloconfiança.length];
                double[] limitesSuperiores = new double[intervaloconfiança.length];


                // Calcula o valor crítico da distribuição t para o nível de confiança especificado
                TDistribution tDistribution = new TDistribution(5);
                double grauDeconfianca=0.95;//parametro
                double valorCritico = tDistribution.inverseCumulativeProbability(1 - (1 - grauDeconfianca) / 2);

                for (int i = 0; i < intervaloconfiança.length; i++) {
                    double desvioPadrao = intervaloconfiança[i];
                    double margemErro = valorCritico * desvioPadrao;
                    limitesInferiores[i] = mlrCoefficients[i] - margemErro;
                    limitesSuperiores[i] = mlrCoefficients[i] + margemErro;
                }

                System.out.println("Intervalo de Confiança (" + (grauDeconfianca * 100) + "%):\nValor Critico:"+valorCritico+"\n\n" );

                for (int i = 0; i < 5; i++) {
                    System.out.println("Intervalo " + (i + 1) + ":[ " + limitesInferiores[i] + " - " + limitesSuperiores[i]+"]");
                }


            } else {
                System.out.println("Invalid choice. Please choose 1 or 2.");
            }


            }
        }



