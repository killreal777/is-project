package itmo.isproject.mapper.user

import itmo.isproject.dto.user.AccountDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.user.Account
import org.mapstruct.Mapper

@Mapper(uses = [UserMapper::class])
interface AccountMapper : EntityMapper<AccountDto, Account>
