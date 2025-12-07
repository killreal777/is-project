package itmo.isproject.repository

import itmo.isproject.model.resource.Resource
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResourceRepository : JpaRepository<Resource, Int>
