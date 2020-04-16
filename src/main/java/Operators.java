public enum Operators {

        MINUS("-",1),
        STAR("*",10),
        LEFT_PARENTHESIS("(",0),
        RIGHT_PARENTHESIS(")",0),
        PLUS("+",1),
        DASH("/",10);

        private String sign;
        private int priority;

        Operators(String sign, int priority){
                this.sign= sign;
                this.priority = priority;
        };

        public int getPriority() {
                return this.priority;
        }
        public String getSign(){
                return this.sign;
        }
        private static Operators getBySign(String sign){
                for(Operators operatorPriority : Operators.values()){
                        if(operatorPriority.getSign().equals(sign)){
                                return operatorPriority;
                        }
                }

                return Operators.values()[0];//not found
        }

        public static int getPriorityOfOperator(String operator){
                return Operators.getBySign(operator).getPriority();
        }


}
