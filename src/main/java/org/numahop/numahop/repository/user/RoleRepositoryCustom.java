package org.numahop.numahop.repository.user;

import org.numahop.numahop.domain.user.Role;
import java.util.List;

public interface RoleRepositoryCustom {

	List<Role> search(String search, final List<String> authorizations);

}
