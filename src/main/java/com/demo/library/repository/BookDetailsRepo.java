package com.demo.library.repository;

import com.demo.library.entity.BookDetails;
import com.demo.library.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BookDetailsRepo extends JpaRepository<BookDetails, Long> {

    Optional<BookDetails> findByIsbnCode(String isbnCode);

    @Query(value = "SELECT * FROM librarydb.book_details WHERE title LIKE CONCAT('%', :title, '%') OR author LIKE CONCAT('%', :author, '%') OR publication_year = :publicationYear", nativeQuery = true)
    List<BookDetails> findBooksByCriteriaNative(@Param("title") String title, @Param("author") String author, @Param("publicationYear") Integer publicationYear);

}
