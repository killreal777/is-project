package itmo.isproject.shared.user.mapper

import itmo.isproject.shared.user.dto.AccountDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.shared.user.model.Account
import org.mapstruct.Mapper

@Mapper(uses = [UserMapper::class])
interface AccountMapper : EntityMapper<AccountDto, Account>
