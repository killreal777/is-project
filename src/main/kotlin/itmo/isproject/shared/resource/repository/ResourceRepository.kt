package itmo.isproject.shared.resource.repository

import itmo.isproject.shared.resource.model.Resource
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResourceRepository : JpaRepository<Resource, Int>