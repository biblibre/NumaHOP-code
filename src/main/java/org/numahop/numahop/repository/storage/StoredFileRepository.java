package org.numahop.numahop.repository.storage;

import org.numahop.numahop.domain.storage.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface StoredFileRepository extends JpaRepository<StoredFile, String> {

}
