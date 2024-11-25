package itmo.is.project.mapper.user;

import itmo.is.project.dto.user.AccountDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.user.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AccountMapper extends EntityMapper<AccountDto, Account> {
}
