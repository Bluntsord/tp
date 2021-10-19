package donnafin.logic.parser;

import static donnafin.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static donnafin.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static donnafin.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.FindCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.ListCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Person;
import donnafin.testutil.PersonBuilder;
import donnafin.testutil.PersonUtil;

/**
This class will act as the test class for parserContext when parserContext's ParserStrategy is ClientViewParser
It should fail the test for AddressBookParser.
 */
public class ClientViewParserContextTest {

    private ClientViewParser clientViewParser = new ClientViewParser();
    private ParserContext parserContext = new ParserContext(clientViewParser);

    @BeforeEach
    public void reset() {
        assertTrue(strategyIsClientParser());
        parserContext = new ParserContext(clientViewParser);
    }

    @Test
    public void parseCommand_clientParserExit() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parserContext.executeParserStrategyCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void executeParserStrategyCommand_clientParserHelp() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parserContext.executeParserStrategyCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void executeParserStrategyCommand_clientParserHome() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(HomeCommand.COMMAND_WORD) instanceof HomeCommand);
        assertTrue(parserContext.executeParserStrategyCommand(
                HomeCommand.COMMAND_WORD + " 3") instanceof HomeCommand);
    }

    @Test
    public void executeParserStrategyCommand_clientParserUnrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), ()
                -> parserContext.executeParserStrategyCommand(""));
    }

    @Test
    public void executeParserStrategyCommand_clientParserUnknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand("unknownCommand"));
    }

    //From here on out we should check that AddressBookParser command fails
    @Test
    public void test_executeParserStrategyCommand_addressBookParserAdd() throws ParseException {
        Person person = new PersonBuilder().build();
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand(PersonUtil.getAddCommand(person)));
    }


    @Test
    public void executeParserStrategyCommand_addressBookParserClear() throws Exception {
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand(ClearCommand.COMMAND_WORD));
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand(ClearCommand.COMMAND_WORD + "3"));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserDelete() throws Exception {
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand(DeleteCommand.COMMAND_WORD));
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand(DeleteCommand.COMMAND_WORD + "3"));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String userInput = FindCommand.COMMAND_WORD + " " + String.join(" ", keywords);
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand(userInput));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserList() throws Exception {
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand(ListCommand.COMMAND_WORD));
    }

    private boolean strategyIsClientParser() {
        return parserContext.getCurrentParserStrategy().equals(clientViewParser);
    }
}
