package Scientific_Submission_Calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;


import static java.lang.Math.E;


/**
 * Created by damitha wijesuriya on 09/12/2016.
 */
public class Scientific_Calculator extends javax.swing.JFrame{
    private JPanel CalculatorJPanel;
    private JTextField InPutText;
    private JButton PercentageBtn;
    private JButton DecimalPointBtn;
    private JButton ZeroBtn;
    private JButton MultiplicationBtn;
    private JButton SevenBtn;
    private JButton EightBtn;
    private JButton NineBtn;
    private JButton DivisionBtn;
    private JButton EqualBtn;
    private JButton PlusOrMinusBtn;
    private JButton SubstractionBtn;
    private JButton SixBtn;
    private JButton ThreeBtn;
    private JButton AdditionBtn;
    private JButton FiveBtn;
    private JButton TwoBtn;
    private JButton FourBtn;
    private JButton OneBtn;
    private JButton AsinBtn;
    private JButton AcosBtn;
    private JButton LogTenBtn;
    private JButton AtanBtn;
    private JButton SinBtn;
    private JButton CosBtn;
    private JButton TanBtn;
    private JButton LnBtn;
    private JButton XToPowerTwoBtn;
    private JButton xYButton;
    private JButton SqrtXBtn;
    private JButton XToPowerThreeBtn;
    private JButton OneOverXBtn;
    private JButton ThreeSqRtXBtn;
    private JButton DBtn;
    private JButton Ebtn;
    private JButton FBtn;
    private JButton BBtn;
    private JButton ABtn;
    private JButton CBtn;
    private JTextField MemoryText;
    private JButton BinBtn;
    private JButton MSBtn;
    private JButton ClearBtn;
    private JButton MRBtn;
    private JButton CEButton;
    private JButton MCBtn;
    private JButton MPlusBtn;
    private JButton HistoryLogBtn;
    private JButton MMinusBtn;
    private JButton GraphBtn;
    private JLabel MemoryLbl;
    private JTextField OutPutText;
    private JRadioButton Degrees;
    private JRadioButton Radians;
    private JButton expbtn;
    private JButton PiButton;
    private JButton ShiftBtn;
    private JLabel error;
    private JButton CloseBracketBtn;
    private JButton OpenBracketBtn;
    private JButton nCrButton;
    private JButton nPrButton;
    private JComboBox Memory;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField FxText;
    private JLabel FxLabel;
    private JComboBox cmb_formula;
    private JButton LoadFomulaBtn;
    private JButton enterButton;
    private JTextField formulaText;
    private JComboBox cmb_source;
    private JButton SaveBtn;
    String Operetor;
    private boolean Zerodisp;
    private boolean Decdisp;
    private double inPuta;
    private double inPutb;
    private double outPut;
    private boolean DegrreDecimal;
    private boolean shift;
    private byte op;
    private double total=0.0;
    private String operator="op";
    private String state;
    private String expresion="cl";
    private int excount;
    private double exptotal=0;
    private String exoperator;
    private String Historylog;
    private String powerstate="close";
    private double powerx;
    private double nvalue;
    private double rvalue;
    private String npstate;
    private String expression = "";
    private String formula;
    private String formula_state;


    private static final ArrayList<Character> DIVIDERS = new ArrayList<Character>
            (Arrays.asList('*', '/', '-', '+'));
    private static final int RIGHT_DIRECTION = 1;
    private static final int LEFT_DIRECTION = -1;

    public static String calc(String expression) {
        int pos = 0;
        //Extracting expression from braces, doing recursive call
        //replace braced expression on result of it solving
        if (-1 != (pos = expression.indexOf("("))) {

            String subexp = extractExpressionFromBraces(expression,pos);
            expression = expression.replace("("+subexp+")", calc(subexp));

            return calc(expression);

            //Three states for calculating sin cos exp
            //input must be like sin0.7
        } else if (-1 != (pos = expression.indexOf("sin"))) {

            pos += 2;//shift index to last symbol of "sin" instead of first

            String number = extractNumber(expression, pos, RIGHT_DIRECTION);

            expression = expression.replace("sin"+number,
                    Double.toString(Math.sin(Double.parseDouble(number))));

            return calc(expression);

        } else if (-1 != (pos = expression.indexOf("cos"))) {

            pos += 2;

            String number = extractNumber(expression, pos, RIGHT_DIRECTION);

            expression = expression.replace("cos"+number,
                    Double.toString(Math.cos(Double.parseDouble(number))));

            return calc(expression);

        } else if (-1 != (pos = expression.indexOf("exp"))) {

            pos += 2;

            String number = extractNumber(expression, pos, RIGHT_DIRECTION);

            expression = expression.replace("exp" + number,
                    Double.toString(Math.exp(Double.parseDouble(number))));

            return calc(expression);


        } else if (expression.indexOf("*") > 0 | expression.indexOf("/") > 0) {

            int multPos = expression.indexOf("*");
            int divPos = expression.indexOf("/");

            pos = Math.min(multPos, divPos);
            if (multPos < 0) pos = divPos; else if (divPos < 0) pos = multPos;
            //If one value of
            //*Pos will be -1 result of min will be incorrect.

            char divider = expression.charAt(pos);

            String leftNum = extractNumber(expression, pos, LEFT_DIRECTION);
            String rightNum = extractNumber(expression, pos, RIGHT_DIRECTION);

            expression = expression.replace(leftNum + divider + rightNum,
                    calcShortExpr(leftNum, rightNum, divider));

            return calc(expression);


        } else if (expression.indexOf("+") > 0 | expression.indexOf("-") > 0) {

            int summPos = expression.indexOf("+");
            int minusPos = expression.indexOf("-");

            pos = Math.min(summPos, minusPos);

            if (summPos < 0) pos = minusPos; else if (minusPos < 0) pos = summPos;

            char divider = expression.charAt(pos);

            String leftNum = extractNumber(expression, pos, LEFT_DIRECTION);
            String rightNum = extractNumber(expression, pos, RIGHT_DIRECTION);

            expression = expression.replace(leftNum + divider + rightNum,
                    calcShortExpr(leftNum, rightNum, divider));

            return calc(expression);

        } else return expression;
    }

    private static String extractExpressionFromBraces(String expression, int pos) {
        int braceDepth = 1;
        String subexp="";

        for (int i = pos+1; i < expression.length(); i++) {
            switch (expression.charAt(i)) {
                case '(':
                    braceDepth++;
                    subexp += "(";
                    break;
                case ')':
                    braceDepth--;
                    if (braceDepth != 0) subexp += ")";
                    break;
                default:
                    if (braceDepth > 0) subexp += expression.charAt(i);

            }
            if (braceDepth == 0 && !subexp.equals("")) return subexp;
        }
        return "Failure!";
    }

    private static String extractNumber(String expression, int pos, int direction) {

        String resultNumber = "";
        int currPos = pos + direction;//shift pos on next symbol from divider

        //For negative numbers
        if (expression.charAt(currPos) == '-') {
            resultNumber+=expression.charAt(currPos);
            currPos+=direction;
        }

        for (; (currPos >= 0) &&
                (currPos < expression.length()) &&
                !DIVIDERS.contains(expression.charAt(currPos));
             currPos += direction) {
            resultNumber += expression.charAt(currPos);
        }

        if (direction==LEFT_DIRECTION) resultNumber = new
                StringBuilder(resultNumber).reverse().toString();

        return resultNumber;
    }

    private static String calcShortExpr(String leftNum, String rightNum, char divider) {
        switch (divider) {
            case '*':
                return Double.toString(Double.parseDouble(leftNum) *
                        Double.parseDouble(rightNum));
            case '/':
                return Double.toString(Double.parseDouble(leftNum) /
                        Double.parseDouble(rightNum));
            case '+':
                return Double.toString(Double.parseDouble(leftNum) +
                        Double.parseDouble(rightNum));
            case '-':
                return Double.toString(Double.parseDouble(leftNum) -
                        Double.parseDouble(rightNum));
            default:
                return "0";
        }

    }

    private static String prepareExpression(String expression) {

        expression = expression.replace("PI", Double.toString(Math.PI));
        expression = expression.replace("E", Double.toString(Math.E));
        expression = expression.replace(" ", "");

        return expression;
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    public static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
    public static double add(double a,double b)
    {
        double result =a+b;
        return result;
    }

    public static double sub(double a,double b)
    {
        double result =a-b;
        return result;
    }
    public static double mul(double a,double b)
    {
        double result =a*b;
        return result;
    }
    public static double div(double a,double b)
    {
        double result =a/b;
        return result;
    }


    public Scientific_Calculator()  {



        add(CalculatorJPanel);
        ZeroBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(state=="op")
                {
                    InPutText.setText("");
                    state="";
                }
                InPutText.setText(InPutText.getText()+"0");
                OutPutText.setText(OutPutText.getText() + "0");

            }
        });
        OneBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

        if(state=="op")
            {
            InPutText.setText("");
            state="";
            }
                InPutText.setText(InPutText.getText()+"1");
                OutPutText.setText(OutPutText.getText() + "1");
            }
        });
        TwoBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(state=="op")
                {
                    InPutText.setText("");
                    state="";
                }
                InPutText.setText(InPutText.getText()+"2");
                OutPutText.setText(OutPutText.getText() + "2");

            }
        });
        ThreeBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(state=="op")
                {
                    InPutText.setText("");
                    state="";
                }
                InPutText.setText(InPutText.getText()+"3");
                OutPutText.setText(OutPutText.getText() + "3");
            }
        });
        FourBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

                if(state=="op")
                {
                    InPutText.setText("");
                    state="";
                }
                InPutText.setText(InPutText.getText()+"4");
                OutPutText.setText(OutPutText.getText() + "4");
            }
        });
        FiveBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(state=="op")
                {
                    InPutText.setText("");
                    state="";
                }
                InPutText.setText(InPutText.getText()+"5");
                OutPutText.setText(OutPutText.getText() + "5");
            }
        });
        SixBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(state=="op")
                {
                    InPutText.setText("");
                    state="";
                }
                InPutText.setText(InPutText.getText()+"6");
                OutPutText.setText(OutPutText.getText() + "6");
            }
        });
        SevenBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(state=="op")
                {
                    InPutText.setText("");
                    state="";
                }
                InPutText.setText(InPutText.getText()+"7");
                OutPutText.setText(OutPutText.getText() + "7");
            }
        });
        EightBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(state=="op")
                {
                    InPutText.setText("");
                    state="";
                }
                InPutText.setText(InPutText.getText()+"8");
                OutPutText.setText(OutPutText.getText() + "8");
            }
        });
        NineBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(state=="op")
                {
                    InPutText.setText("");
                    state="";
                }
                InPutText.setText(InPutText.getText()+"9");
                OutPutText.setText(OutPutText.getText() + "9");
            }
        });
        DecimalPointBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(!Decdisp )
                {
                    InPutText.setText(InPutText.getText() + ".");
                    Decdisp=true;
                }
            }
        });

              PlusOrMinusBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                inPutb = Double.parseDouble(String.valueOf(InPutText.getText()));
                outPut = inPutb * -1;

                if (outPut > -1000000000 && outPut < 100000000) {
                    InPutText.setText(String.valueOf(outPut));
                    OutPutText.setText(String.valueOf(outPut));
                }
                else
                {
                    InPutText.setText("Error");
                }
                Decdisp=true;
                outPut=0;
            }
        });


        ClearBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText("");
            }
        });

        CEButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    PrintWriter out = new PrintWriter("D:/calHistory.txt");
                    out.println( InPutText.getText() );

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                InPutText.setText("");
              OutPutText.setText("");
              total=0;
              exptotal=0;
              expresion="cl";
              operator="op";
              error.setText("");
            }
        });

        OneOverXBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

                inPutb=Double.parseDouble(String.valueOf(InPutText.getText()));
                outPut=1 / inPutb;

                if (outPut > -1000000000 && outPut < 100000000) {
                    InPutText.setText(String.valueOf(outPut));
                }
                else
                {
                    InPutText.setText("Error");
                }
                OutPutText.setText("1/ "+String.valueOf(inPutb));
                outPut=0;
                op=0;

            }
        });
        PiButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "π");
                if(total==0)
                {
                    total=Double.parseDouble(InPutText.getText());
                }
                if(operator=="pi")
                {
                    total=(total*22/7);
                    InPutText.setText(String.valueOf(total));

                }

                if(operator=="%")
                {
                    total=(total*Double.parseDouble(InPutText.getText()))/100;
                    InPutText.setText(String.valueOf(total));

                }

                if(operator=="+")
                {
                    total=total+Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="*")
                {
                    total=total*Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="/")
                {
                    total=total/Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="-")
                {
                    total=total-Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                total=total*3.14159265;
                InPutText.setText(String.valueOf(total));
                state="op";

            }
        });
                XToPowerTwoBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                inPutb=Double.parseDouble(String.valueOf(InPutText.getText()));
                outPut=inPutb*inPutb;

                if (outPut > -1000000000 && outPut < 100000000) {
                    InPutText.setText(String.valueOf(outPut));
                }
                else
                {
                    InPutText.setText("Error");
                }
                OutPutText.setText(String.valueOf(inPutb) + "²");
                outPut=0;
                op=0;
            }
        });
        SqrtXBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                inPutb=Double.parseDouble(String.valueOf(InPutText.getText()));
                outPut=Math.sqrt(inPutb);
                InPutText.setText(String.valueOf(outPut));

                OutPutText.setText("√ "+String.valueOf(inPutb) );
                outPut=0;
                op=0;
            }
        });
        XToPowerThreeBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                inPutb=Double.parseDouble(String.valueOf(InPutText.getText()));
                outPut=inPutb*inPutb*inPutb;

                if (outPut > -1000000000 && outPut < 100000000) {
                    InPutText.setText(String.valueOf(outPut));
                }
                else
                {
                    InPutText.setText("Error");
                }
                OutPutText.setText(String.valueOf(inPutb) + "³");
                outPut=0;
                op=0;
            }
        });
                ThreeSqRtXBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                inPutb=Double.parseDouble(String.valueOf(InPutText.getText()));
                outPut=Math.cbrt(inPutb);
                InPutText.setText(String.valueOf(outPut));

                OutPutText.setText("³√ "+String.valueOf(inPutb) );
                outPut=0;
                op=0;
            }
        });


        SinBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "Sin");
                if(total==0)
                {
                    total=Double.parseDouble(InPutText.getText());
                }
                if(operator=="pi")
                {
                    total=(total*22/7);
                    InPutText.setText(String.valueOf(total));

                }

                if(operator=="%")
                {
                    total=(total*Double.parseDouble(InPutText.getText()))/100;
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="+")
                {
                    total=total+Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="*")
                {
                    total=total*Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="/")
                {
                    total=total/Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="-")
                {
                    total=total-Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                total=Math.toRadians(Double.parseDouble(InPutText.getText()));
                InPutText.setText(String.valueOf(total));

            }
        });
        CosBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "cos");


                if(total==0)
                {
                    total=Double.parseDouble(InPutText.getText());
                }
                if(operator=="pi")
                {
                    total=(total*22/7);
                    InPutText.setText(String.valueOf(total));

                }

                if(operator=="%")
                {
                    total=(total*Double.parseDouble(InPutText.getText()))/100;
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="+")
                {
                    total=total+Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="*")
                {
                    total=total*Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="/")
                {
                    total=total/Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="-")
                {
                    total=total-Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                total=Math.cos(Math.toRadians(Double.parseDouble(InPutText.getText())));
                InPutText.setText(String.valueOf(total));

            }
        });
        TanBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "tan");
                if(total==0)
                {
                    total=Double.parseDouble(InPutText.getText());
                }
                if(operator=="pi")
                {
                    total=(total*22/7);
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="%")
                {
                    total=(total*Double.parseDouble(InPutText.getText()))/100;
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="+")
                {
                    total=total+Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="*")
                {
                    total=total*Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="/")
                {
                    total=total/Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="-")
                {
                    total=total-Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                total=Math.tan(Math.toRadians(Double.parseDouble(InPutText.getText())));
                InPutText.setText(String.valueOf(total));
            }
        });

        AdditionBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

                OutPutText.setText(OutPutText.getText() + "+");
                if(expresion=="cl") {

                    if (total == 0) {
                        total = Double.parseDouble(InPutText.getText());
                    }
                    if (operator == "%") {
                        total = (total * Double.parseDouble(InPutText.getText())) / 100;
                        InPutText.setText(String.valueOf(total));
                    }

                    if (operator == "+") {
                        total = total + Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "*") {
                        total = total * Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "/") {
                        total = total / Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "-") {
                        total = total - Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    operator = "+";
                    state = "op";
                }
            }
        });
        SubstractionBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "-");
                if(expresion=="cl") {

                    if (total == 0) {
                        total = Double.parseDouble(InPutText.getText());
                    }
                    if (operator == "%") {
                        total = (total * Double.parseDouble(InPutText.getText())) / 100;
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "+") {

                        total = total + Double.parseDouble(InPutText.getText());

                        InPutText.setText(String.valueOf(total));

                    }
                    if (operator == "*") {
                        total = total * Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));

                    }
                    if (operator == "/") {
                        total = total / Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));

                    }
                    if (operator == "-") {
                        total = total - Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    operator = "-";
                    state = "op";
               }


            }
        });
        MultiplicationBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

                OutPutText.setText(OutPutText.getText() + "*");

                if(expresion=="cl") {

                    if (total == 0) {
                        total = Double.parseDouble(InPutText.getText());
                    }
                    if (operator == "%") {
                        total = (total * Double.parseDouble(InPutText.getText())) / 100;
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "+") {
                        total = total + Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "*") {
                        total = total * Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "/") {
                        total = total / Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "-") {
                        total = total - Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    operator = "*";
                    state = "op";
                }
            }
        });

        DivisionBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "/");
                if(expresion=="cl") {

                    if (total == 0) {
                        total = Double.parseDouble(InPutText.getText());
                    }
                    if (operator == "%") {
                        total = (total * Double.parseDouble(InPutText.getText())) / 100;
                        InPutText.setText(String.valueOf(total));
                    }

                    if (operator == "+") {
                        total = total + Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));

                    }
                    if (operator == "*") {
                        total = total * Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "/") {
                        total = total / Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "-") {
                        total = total - Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    operator = "/";
                    state = "op";
                }
            }
        });
        PercentageBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "%");

                if(expresion=="cl") {

                    if (total == 0) {
                        total = Double.parseDouble(InPutText.getText());
                    }
                    if (operator == "%") {
                        total = (total * Double.parseDouble(InPutText.getText())) / 100;
                        InPutText.setText(String.valueOf(total));
                    }

                    if (operator == "+") {
                        total = total + Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    if (operator == "*") {
                        total = total * Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));

                    }
                    if (operator == "/") {
                        total = total / Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));

                    }
                    if (operator == "-") {
                        total = total - Double.parseDouble(InPutText.getText());
                        InPutText.setText(String.valueOf(total));
                    }
                    operator = "%";
                    state = "op";
                }
            }
        });
        EqualBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

                if(excount==0&&expresion=="op")
                {
                operator="";
                total=Double.parseDouble(calc(OutPutText.getText()));
                    InPutText.setText(Double.toString(total));
                    expresion="cl";
                }
                if (total == 0) {
                    total = Double.parseDouble(InPutText.getText());
                }
                if (operator == "%") {
                    total = (total * Double.parseDouble(InPutText.getText())) / 100;
                    InPutText.setText(String.valueOf(total));
                }

                if (operator == "+") {
                    total = total + Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                if (operator == "*") {
                    total = total * Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if (operator == "/") {
                    total = total / Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                if (operator == "-") {
                    total = total - Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                if(powerstate=="open")
                {
                    double a= Math.pow(powerx,Double.parseDouble(InPutText.getText()));
                    powerstate="close";
                    InPutText.setText(Double.toString(a));
                    total=total+a;
                    total=Double.parseDouble(InPutText.getText());
                }
                if(npstate=="C")
                {
                    double n = nvalue;
                    int resultn = 1;
                    for (int i = 1; i <= n; i++) {
                        resultn = resultn * i;
                    }
                    double r = Double.parseDouble(InPutText.getText());
                    int resultr = 1;
                    for (int i = 1; i <= r; i++) {
                        resultr = resultr * i;
                    }
                   double a= ((resultn-resultr)*resultr);
                    a=resultn/a;
                    npstate="";
                    InPutText.setText(Double.toString(a));
                    total=a;
                }

                if(npstate=="P")
                {
                    double n = nvalue;
                    int resultn = 1;
                    for (int i = 1; i <= n; i++) {
                        resultn = resultn * i;
                    }
                    double r = Double.parseDouble(InPutText.getText());
                    int resultr = 1;
                    for (int i = 1; i <= r; i++) {
                        resultr = resultr * i;
                    }
                    double a= resultn/((resultn-resultr));

                    npstate="";
                    InPutText.setText(Double.toString(a));
                }
                operator="";
            }
        });
        OneBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("1");
            }
        });

        AsinBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "Asin");
                if(total==0)
                {
                    total=Double.parseDouble(InPutText.getText());
                }
                if(operator=="pi")
                {
                    total=(total*22/7);
                    InPutText.setText(String.valueOf(total));
                }
                if(operator=="%")
                {
                    total=(total*Double.parseDouble(InPutText.getText()))/100;
                    InPutText.setText(String.valueOf(total));
                }
                if(operator=="+")
                {
                    total=total+Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                if(operator=="*")
                {
                    total=total*Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                if(operator=="/")
                {
                    total=total/Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="-")
                {
                    total=total-Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                total=Math.asin(Math.toRadians(Double.parseDouble(InPutText.getText())));
                InPutText.setText(String.valueOf(total));
            }
        });
        AcosBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "acos");
                if(total==0)
                {
                    total=Double.parseDouble(InPutText.getText());
                }
                if(operator=="pi")
                {
                    total=(total*22/7);
                    InPutText.setText(String.valueOf(total));
                }

                if(operator=="%")
                {
                    total=(total*Double.parseDouble(InPutText.getText()))/100;
                    InPutText.setText(String.valueOf(total));

                }

                if(operator=="+")
                {
                    total=total+Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="*")
                {
                    total=total*Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="/")
                {
                    total=total/Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="-")
                {
                    total=total-Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));
                }
                total=Math.acos(Math.toRadians(Double.parseDouble(InPutText.getText())));
                InPutText.setText(String.valueOf(total));
            }
        });
        AtanBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(OutPutText.getText() + "atan");


                if(total==0)
                {
                    total=Double.parseDouble(InPutText.getText());
                }
                if(operator=="pi")
                {
                    total=(total*22/7);
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="%")
                {
                    total=(total*Double.parseDouble(InPutText.getText()))/100;
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="+")
                {
                    total=total+Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="*")
                {
                    total=total*Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="/")
                {
                    total=total/Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                if(operator=="-")
                {
                    total=total-Double.parseDouble(InPutText.getText());
                    InPutText.setText(String.valueOf(total));

                }
                total=Math.atan(Math.toRadians(Double.parseDouble(InPutText.getText())));
                InPutText.setText(String.valueOf(total));
            }
        });
        GraphBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int size = 500;

                double value = Double.parseDouble(FxText.getText());

                JFrame frame = new JFrame("Plot Function");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                PlotFunctionPanel panel = new PlotFunctionPanel(size, value);
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });
        OpenBracketBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                OutPutText.setText(OutPutText.getText()+"(");
            excount=excount+1;
            state="op";
            expresion="op";

            }
        });
        CloseBracketBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                excount=excount-1;
                OutPutText.setText(OutPutText.getText()+")");
            }
        });
        MPlusBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                Memory.addItem(InPutText.getText());

                try {
                    PrintWriter out = new PrintWriter("D:/calHistory.txt");
                    out.println( InPutText.getText() );
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        MMinusBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

                Memory.removeItemAt(0);
            }
        });
        MCBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                Memory.removeAllItems();
            }
        });
        MRBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText(Memory.getSelectedItem().toString());
            }
        });

        HistoryLogBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    PrintWriter out = new PrintWriter("D:/calHistory.txt");
                    out.write(Historylog);
                    out.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        comboBox3.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(comboBox2.getSelectedItem()=="Decimal"&&comboBox3.getSelectedItem()=="Binary")
                {

                    InPutText.setText(Integer.toBinaryString(Integer.parseInt(InPutText.getText())));
                }

                if(comboBox2.getSelectedItem()=="Decimal"&&comboBox3.getSelectedItem()=="Octal")
                {

                    InPutText.setText(Integer.toOctalString(Integer.parseInt(InPutText.getText())));
                }

                if(comboBox2.getSelectedItem()=="Decimal"&&comboBox3.getSelectedItem()=="Hexadecimal")
                {

                    InPutText.setText(Integer.toHexString(Integer.parseInt(InPutText.getText())));
                }

                if(comboBox2.getSelectedItem()=="Binary"&&comboBox3.getSelectedItem()=="Decimal")
                {
                    String val=InPutText.getText();
                    int vall=Integer.parseInt(val, 2);
                    InPutText.setText(Integer.toString(vall));
                }

                if(comboBox2.getSelectedItem()=="Binary"&&comboBox3.getSelectedItem()=="Octal")
                {
                    String val=InPutText.getText();
                    int vall=Integer.parseInt(val, 8);
                    InPutText.setText(Integer.toString(vall));

                }
                if(comboBox2.getSelectedItem()=="Binary"&&comboBox3.getSelectedItem()=="Hexadecimal")
                {
                    String val=InPutText.getText();
                    int vall=Integer.parseInt(val, 16);
                    InPutText.setText(Integer.toString(vall));
                }

                if(comboBox2.getSelectedItem()=="Octal"&&comboBox3.getSelectedItem()=="Binary")
                {
                    InPutText.setText(Integer.toBinaryString(Integer.parseInt(InPutText.getText())));

                }

                if(comboBox2.getSelectedItem()=="Octal"&&comboBox3.getSelectedItem()=="Decimal")
                {
                    String octalNo=InPutText.getText();
                    InPutText.setText(Long.toHexString(Long.parseLong(octalNo,8)));

                }

                if(comboBox2.getSelectedItem()=="Hexadecimal"&&comboBox3.getSelectedItem()=="Decimal")
                {
                    String inputHex=InPutText.getText();
                    Integer outputDecimal = Integer.parseInt(inputHex, 16);
                    InPutText.setText(Integer.toString(outputDecimal));


                }

                if(comboBox2.getSelectedItem()=="Hexadecimal"&&comboBox3.getSelectedItem()=="Binary")
                {
                    int num = (Integer.parseInt(InPutText.getText(), 16));
                    InPutText.setText(Integer.toBinaryString(num));


                }

                if(comboBox2.getSelectedItem()=="Hexadecimal"&&comboBox3.getSelectedItem()=="Octal")
                {

                    int dec = Integer.parseInt(InPutText.getText(),16);

                    String oct = Integer.toOctalString(dec);
                    InPutText.setText(oct);
                }

            }
        });
        ABtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText("a");
            }
        });
        BBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText("b");
            }
        });
        CBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText("c");
            }
        });
        DBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText("d");
            }
        });
        Ebtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText("e");
            }
        });
        FBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText("f");
            }
        });
        xYButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

                    powerx=Double.parseDouble(InPutText.getText());
                    OutPutText.setText(OutPutText.getText()+"^");
                    powerstate="open";
                state = "op";

            }
        });
        LogTenBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText(Double.toString(Math.log10(Double.parseDouble(InPutText.getText()))));
            }
        });
        LnBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText(Double.toString(Math.log(Double.parseDouble(InPutText.getText()))));
            }
        });
        expbtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                InPutText.setText(Double.toString(Math.pow(E,Double.parseDouble(InPutText.getText()))));
            }
        });
        nCrButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(InPutText.getText()+"C");
                npstate="C";
                state="op";
                nvalue=Double.parseDouble(InPutText.getText());
            }
        });
        nPrButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                OutPutText.setText(InPutText.getText()+"P");
                npstate="P";
                state="op";
                nvalue=Double.parseDouble(InPutText.getText());
            }
        });
        LoadFomulaBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

                String file = "D:/formula.txt";

                try {

                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String line;
                    // Uncomment the line below if you want to skip the fist line (e.g if headers)
                    // line = br.readLine();

                    while ((line = br.readLine()) != null) {

                        cmb_formula.addItem(line);

                    }
                    br.close();

                } catch (IOException ee) {
                    System.out.println("ERROR: unable to read file " + file);
                    ee.printStackTrace();
                }
                try
                {
                    Connection con = DB.getConnection();
                    Statement s = con.createStatement();
                    ResultSet r = con.createStatement().executeQuery("select * from formula");
                    while (r.next()) {
                        cmb_formula.addItem( r.getString(1));
                    }
                }
                catch (Exception d)
                {

                }
            }
        });

        CalculatorJPanel.addMouseMotionListener(new MouseMotionAdapter() {
        });
        cmb_formula.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(!cmb_formula.getSelectedItem().toString().equals("-"))
                {
                    OutPutText.setText(cmb_formula.getSelectedItem().toString());
                    error.setText("Enter Value For a: and press enter");
                    String fx=cmb_formula.getSelectedItem().toString();
                    formula = fx.substring(fx.indexOf("=") + 1);

                    formula_state="a";
                }
            }
        });

        enterButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(formula_state=="a")
                {
                    formula=formula.replace("a",InPutText.getText());
                    formula_state="b";
                    error.setText("Enter Value For b: and press enter");
                    InPutText.setText("");
                }

                if(formula_state=="b"&&!InPutText.getText().equals(""))
                {
                    formula=formula.replace("b",InPutText.getText());
                    formula_state="c";
                    error.setText("Enter Value For c: and press enter");
                    InPutText.setText("");
                }

                if(formula_state=="c"&&!InPutText.getText().equals(""))
                {
                    formula=formula.replace("c",InPutText.getText());
                    formula_state="";
                    error.setText("Answer Is");
                    InPutText.setText("");
                    total=Double.parseDouble(calc(formula));
                    InPutText.setText(Double.toString(total));
                }
            }
        });
        SaveBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if(cmb_source.getSelectedItem().toString().equals("Save in File"))
                {
                    cmb_formula.addItem(formulaText.getText());
                    int itemcout=cmb_formula.getItemCount();

                    try {
                        PrintWriter out = new PrintWriter("D:/formula.txt");
                        for(int i=0;i<itemcout;i++) {


                            out.write(cmb_formula.getItemAt(i).toString());
                            out.write(System.getProperty("line.separator"));
                        }
                        out.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                if(cmb_source.getSelectedItem().toString().equals("Save in File")) {
                   try {
                       Connection con = DB.getConnection();
                       Statement s = con.createStatement();
                       s.executeUpdate("insert into formula values('" + formulaText.getText()+ "')");
                   }
                   catch (Exception f)
                   {

                   }

                }
                }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {

        Scientific_Calculator Sc=new Scientific_Calculator();
        Sc.setVisible(true);
        Sc.setSize(800,600);
        Sc.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
