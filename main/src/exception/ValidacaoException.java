package exception;

public class ValidacaoException extends IllegalArgumentException
{
    public ValidacaoException() {
        super("Campo incorreto.");
    }

    public ValidacaoException(String s) {
        super(s);
    }
}
