package org.cowary.airmonitorback.converter;

import org.cowary.airmonitorback.db.entity.Command;
import org.cowary.airmonitorback.dto.CommandRq;
import org.cowary.airmonitorback.dto.CommandRs;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface CommandConverter {
    CommandConverter INSTANCE = Mappers.getMapper(CommandConverter.class);

    CommandRs commandToCommandRs(Command command);
    Command commandRqToCommand(CommandRq commandRq);
    List<CommandRs> commandListToCommandRsList(List<Command> commands);
    List<Command> commandRsListToCommandList(List<CommandRs> commands);
}
