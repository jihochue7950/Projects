package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Category;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

	@Query("SELECT COUNT(*) FROM Book")
	public Long countTotalBooks();
	
	@Query("SELECT b FROM Book b WHERE CONCAT(b.title,' ',b.author, ' ', b.publisher) LIKE %?1%")
	public List<Book> findAll(String keyword);

	@Query("SELECT b FROM Book b WHERE CONCAT(b.title,' ',b.author, ' ', b.publisher) LIKE %?1%")
	public Page<Book> findAll(String keyword, Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.category =:category")
	public Page<Book> findAll(@Param("category")Category category, Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE CONCAT(b.title,' ',b.author, ' ', b.publisher) LIKE %:keyword% AND b.category =:category")
	public Page<Book> findAll(@Param("keyword") String keyword, @Param("category")Category category, Pageable pageable);

	
	
	
	
	@Query("SELECT COUNT(DISTINCT b.bookId) FROM Book b JOIN OrderDetail o ON b.bookId = o.book.bookId")
	public Long countBestBooks();
	
	@Query("SELECT b FROM Book b JOIN OrderDetail o ON b.bookId = o.book.bookId GROUP BY b.bookId ORDER BY COUNT(o.book.bookId) DESC")
	public List<Book> bestseller();
	
	@Query("SELECT b FROM Book b JOIN OrderDetail o ON b.bookId = o.book.bookId GROUP BY b.bookId ORDER BY COUNT(o.book.bookId) DESC")
	public Page<Book> bestseller(Pageable pageable);

	@Query("SELECT b FROM Book b JOIN OrderDetail o ON b.bookId = o.book.bookId WHERE CONCAT(b.title, ' ', b.author, ' ', b.publisher) LIKE %?1% GROUP BY b.bookId ORDER BY COUNT(o.book.bookId ) DESC")
	public Page<Book> bestseller(String keyword, Pageable pageable);
	
	@Query("SELECT b FROM Book b JOIN OrderDetail o ON b.bookId = o.book.bookId WHERE b.category =:category GROUP BY b.bookId ORDER BY COUNT(o.book.bookId ) DESC")
	public Page<Book> bestseller(@Param("category")Category category, Pageable pageable);
	
	@Query("SELECT b FROM Book b JOIN OrderDetail o ON b.bookId = o.book.bookId WHERE CONCAT(b.title, ' ', b.author, ' ', b.publisher) LIKE %:keyword% AND b.category =:category GROUP BY b.bookId ORDER BY COUNT(o.book.bookId ) DESC")
	public Page<Book> bestseller(@Param("keyword") String keyword, @Param("category")Category category, Pageable pageable);
	
	
	
	
	
	@Query("SELECT b FROM Book b ORDER BY b.publicationDate DESC")
	public List<Book> finByNewBook();

	@Query("SELECT b FROM Book b ORDER BY b.publicationDate DESC")
	public Page<Book> findByNewBook(Pageable pageable);

	@Query("SELECT b FROM Book b WHERE CONCAT(b.title, ' ', b.author, ' ', b.publisher) LIKE %?1% ORDER BY b.publicationDate DESC")
	public Page<Book> findByNewBook(String keyword, Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.category =:category ORDER BY b.publicationDate DESC")
	public Page<Book> findByNewBook(@Param("category")Category category, Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE CONCAT(b.title, ' ', b.author, ' ', b.publisher) LIKE %:keyword% AND b.category =:category ORDER BY b.publicationDate DESC")
	public Page<Book> findByNewBook(@Param("keyword") String keyword, @Param("category")Category category, Pageable pageable);

	@Query("SELECT COUNT(b) FROM Book b WHERE b.discountRate <> 0")
	public Long countTotaldiscountBook();
	
	@Query("SELECT b FROM Book b WHERE b.discountRate <> 0")
	public List<Book> findDiscountBooks();
	
	
	
	
   @Query("SELECT b FROM Book b WHERE b.discountRate>1")
   public Page<Book> findBySaleBook(Pageable pageable);
   
   @Query("SELECT b FROM Book b WHERE b.discountRate>1 AND CONCAT(b.title, ' ', b.author, ' ', b.publisher) LIKE %?1%")
   public Page<Book> findBySaleBook(String keyword, Pageable pageable);
   
   @Query("SELECT b FROM Book b WHERE b.discountRate>1 AND CONCAT(b.title, ' ', b.author, ' ', b.publisher) LIKE %:keyword% AND b.category =:category")
   public Page<Book> findBySaleBook(@Param("keyword") String keyword, @Param("category")Category category, Pageable pageable);
   
   @Query("SELECT b FROM Book b WHERE b.discountRate>1 AND b.category =:category")
   public Page<Book> findBySaleBook(@Param("category")Category category, Pageable pageable);
	
	
	
	
	
	
	
	@Query("SELECT b FROM Book b ORDER BY RAND()") // LIMIT 5는 안됨
	public List<Book> findRandomBooks();

	@Query("SELECT b FROM Book b WHERE b.category = :category or b.category.parent =:category")
	public List<Book> findByCategoryId(@Param("category") Category category);
	
	@Query("SELECT b FROM Book b JOIN BooksBranch bb ON b.bookId = bb.book WHERE bb.branches.branchId = :branchId")
	public List<Book> findByBranch(@Param("branchId") int branchId);

	@Query("SELECT b FROM Book b ORDER BY b.price DESC")
	public List<Book> sortprice();

	@Query("SELECT b FROM Book b ORDER BY b.title ASC")
	public List<Book> sortTitle();



	
	





	
	

}