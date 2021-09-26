package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

public class RemarkCommandParser implements Parser<RemarkCommand> {

    @Override
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Returns an argument multimap of all the positions
        ArgumentMultimap multimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);
        Index index;

        try {
            index = ParserUtil.parseIndex(multimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), pe);
        }

        String remark = multimap.getValue(PREFIX_REMARK).orElse("");
        return new RemarkCommand(index, new Remark(remark));
    }
}
