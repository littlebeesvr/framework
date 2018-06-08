package leap.lang.el.spel.parser;

//from alibaba simple el project
enum Token {
	BOOLEAN("boolean"),
	BREAK("break"),
	BYTE("byte"),
	CASE("case"),
	CATCH("catch"),
	CHAR("char"),
	CLASS("class"),
	CONST("const"),
	CONTINUE("continue"),
	DEFAULT("default"),
	DO("do"),
	DOUBLE("dobule"),
	ELSE("else"),
	ENUM("enum"),
	EXTENDS("extends"),
	FINAL("final"),
	FINALLY("finally"),
	FLOAT("float"),
	FOR("for"),
	IF("if"),
	GOTO("goto"),
	IMPLEMENTS("implements"),
	IMPORT("import"),
	INSTNACEOF("instanceof"),
	INT("int"),
	INTERFACE("interface"),
	LONG("long"),
	NATIVE("native"),
	NEW("new"),
	PACKAGE("package"),
	PRIVATE("private"),
	PROTECTED("protected"),
	PUBLIC("public"),
	RETURN("return"),
	SHORT("short"),
	STATIC("static"),
	STRICTFP("strictfp"),
	SUPER("super"),
	SWITCH("switch"),
	SYNCHRONIZED("synchronized"),
	THIS("this"),
	THROW("throw"),
	THROWS("throws"),
	TRANSIENT("transient"),
	TRY("try"),
	VOID("void"),
	VOLATILE("volatile"),
	WHILE("while"),
    CONTAINS("contains"),
    STARTS_WITH("startsWith"),
    ENDS_WITH("endsWith"),
	
	NULL("null"),
	TRUE("true"),
	FALSE("false"),
   
	LPAREN("("),
	RPAREN(")"),
	LBRACE("{"),
	RBRACE("}"),
	LBRACKET("["),
	RBRACKET("]"),
	SEMI(";"),
	COMMA(","),
	DOT("."),
	EQ("="),
	PLUSEQ("+="),
	SUBEQ("-="),
	GT(">"),
	LT("<"),
	BANG("!"),
	TILDE("~"),
	QUES("?"),
	COLON(":"),
	COLONEQ(":="),
	EQEQ("=="),
	LTEQ("<="),
	LTEQGT("<=>"),
	LTGT("<>"),
	GTEQ(">="),
	BANGEQ("!="),
	BANGGT("!>"),
	BANGLT("!<"),
	AMPAMP("&&"),
	BARBAR("||"),
	PLUS("+"),
	PLUSPLUS("++"),
	SUB("-"),
	SUBSUB("--"),
	STAR("*"),
	SLASH("/"),
	AMP("&"),
	BAR("|"),
	CARET("^"),
	PERCENT("%"),
	LTLT("<<"),
	GTGT(">>"),
	
	START_EVAL_DYNAMIC("${"),
	END_EVAL("}"),
	TEXT,
	
	EOF,
	ERROR,
	IDENTIFIER,
	LITERAL_STRING,
	LITERAL_INT,
	LITERAL_FLOAT,
	LITERAL_DOUBLE,
	LITERAL_HEX,
	LITERAL_HEX_LONG
    ;
		
    public final String name;

    Token() {
        this(null);
    }

    Token(String name) {
        this.name = name;
    }
}
