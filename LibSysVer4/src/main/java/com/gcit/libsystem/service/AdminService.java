package com.gcit.libsystem.service;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.*;
import java.sql.*;
import java.util.*;

import com.gcit.libsystem.dao.*;
import com.gcit.libsystem.entity.*;

@RestController
@RequestMapping(value = "/admin")
public class AdminService {

	@Autowired
	BookDao 	 bookDao;
	@Autowired
	AuthorDao 	 authorDao;
	@Autowired
	PublisherDao publisherDao;
	@Autowired
	GenreDao 	 genreDao;
	@Autowired
	BranchDao 	 branchDao;
	@Autowired
	BorrowerDao  borrowerDao;
	@Autowired
	BookLoanDao  bookLoanDao;
	
	@Transactional
	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, consumes="application/json")
	public void addAuthor(@RequestBody Author author) {
		try {
			authorDao.addAuthor(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/initAuthor", method = RequestMethod.GET, produces="application/json")
	public Author initAuthor() {
		return new Author();
	}
	
	@Transactional
	@RequestMapping(value = "/addBook", method = RequestMethod.POST, consumes="application/json")
	public void addBook(@RequestBody Book book){
			try {
				book.setBookId(bookDao.addBookReplyID(book));
				bookDao.addBook(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	@RequestMapping(value = "/initBook", method = RequestMethod.GET, produces="application/json")
	public Book initBook() {
		return new Book();
	}
	
	@Transactional
	@RequestMapping(value = "/addGenre", method = RequestMethod.POST, consumes="application/json")
	public void addGenre(@RequestBody Genre genre){
		try {
			genreDao.addGenre(genre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/initGenre", method = RequestMethod.GET, produces="application/json")
	public Genre initGenre() {
		return new Genre();
	}
	
	@Transactional
	@RequestMapping(value = "/addPublisher", method = RequestMethod.POST, consumes="application/json")
	public void addPublisher(@RequestBody Publisher publisher){
		try {
			publisherDao.addPublisher(publisher);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/initPublisher", method = RequestMethod.GET, produces="application/json")
	public Publisher initPublisher() {
		return new Publisher();
	}
	
	@Transactional
	@RequestMapping(value = "/addBranch", method = RequestMethod.POST, consumes="application/json")
	public void addBranch(@RequestBody Branch branch){
		try {
			branchDao.addBranch(branch);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/initBranch", method = RequestMethod.GET, produces="application/json")
	public Branch initBranch() {
		return new Branch();
	}
	
	@Transactional
	@RequestMapping(value = "/addBorrower", method = RequestMethod.POST, consumes="application/json")
	public void addBorrower(@RequestBody Borrower borrower){
		try {
			borrowerDao.addBorrower(borrower);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/initBorrower", method = RequestMethod.GET, produces="application/json")
	public Borrower initBorrower() {
		return new Borrower();
	}
	
	@Transactional
	@RequestMapping(value = "/updateAuthor", method = RequestMethod.POST, consumes="application/json")
	public void updateAuthor(@RequestBody Author author){
		try {
			authorDao.updateAuthor(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@RequestMapping(value = "/updateBook", method = RequestMethod.POST, consumes="application/json")
	public void updateBook(@RequestBody Book book){
			try {
				bookDao.updateBook(book);
				bookDao.deleteBookAuthors(book);
				bookDao.addBookAuthors(book);
				bookDao.deleteBookGenres(book);
				bookDao.addBookGenres(book);
				bookDao.updateBookPublisher(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	@Transactional
	@RequestMapping(value = "/updateBookWAuthors", method = RequestMethod.POST, consumes="application/json")
	public void updateOnlyBook(@RequestBody Book book){
			try {
				bookDao.updateBook(book);
				bookDao.deleteBookAuthors(book);
				bookDao.addBookAuthors(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	@Transactional
	@RequestMapping(value = "/updateBookWGenres", method = RequestMethod.POST, consumes="application/json")
	public void updateBookWGenres(@RequestBody Book book){
			try {
				bookDao.updateBook(book);
				bookDao.deleteBookGenres(book);
				bookDao.addBookGenres(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	@Transactional
	@RequestMapping(value = "/updateBookWPubs", method = RequestMethod.POST, consumes="application/json")
	public void updateBookWPubs(@RequestBody Book book){
			try {
				bookDao.updateBook(book);
				bookDao.updateBookPublisher(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	@Transactional
	@RequestMapping(value = "/updateGenre", method = RequestMethod.POST, consumes="application/json")
	public void updateGenre(@RequestBody Genre genre){
		try {
			genreDao.updateGenre(genre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@RequestMapping(value = "/updatePublisher", method = RequestMethod.POST, consumes="application/json")
	public void updatePublisher(@RequestBody Publisher publisher){
		try {
			publisherDao.updatePublisherName(publisher);
			publisherDao.updatePublisherAddress(publisher);
			publisherDao.updatePublisherPhone(publisher);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Transactional
	@RequestMapping(value = "/updateBranch", method = RequestMethod.POST, consumes="application/json")
	public void updateBranch(@RequestBody Branch branch){
		try {
			branchDao.updateBranchName(branch);
			branchDao.updateBranchAddress(branch);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
 	
	@Transactional
	@RequestMapping(value = "/updateBorrower", method = RequestMethod.POST, consumes="application/json")
	public void updateBorrower(@RequestBody Borrower borrower){
		try {
			borrowerDao.updateBorrowerName(borrower);
			borrowerDao.updateBorrowerAddress(borrower);
			borrowerDao.updateBorrowerPhone(borrower);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@RequestMapping(value = "/deleteAuthor", method = RequestMethod.POST, consumes="application/json")
	public void deleteAuthor(@RequestBody Author author){
		try {
			authorDao.deleteAuthor(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST, consumes="application/json")
	public void deleteBook(@RequestBody Book book){
		try {
			bookDao.deleteBook(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@RequestMapping(value = "/deleteGenre", method = RequestMethod.POST, consumes="application/json")
	public void deleteGenre(@RequestBody Genre genre){
		try {
			genreDao.deleteGenre(genre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@RequestMapping(value = "/deletePublisher", method = RequestMethod.POST, consumes="application/json")
	public void deletePublisher(@RequestBody Publisher publisher){
		try {
			publisherDao.deletePublisher(publisher);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@RequestMapping(value = "/deleteBranch", method = RequestMethod.POST, consumes="application/json")
	public void deleteBranch(@RequestBody Branch branch){
		try {
			branchDao.deleteBranch(branch);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/deleteBorrower", method = RequestMethod.POST, consumes="application/json")
	public void deleteBorrower(@RequestBody Borrower borrower){
		try {
			borrowerDao.deleteBorrower(borrower);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@RequestMapping(value = "/updateDueDate", method = RequestMethod.POST, consumes="application/json")
	public void updateDueDate(@RequestBody BookLoan bookLoan){
		try {
			bookLoanDao.updateBookLoanDate(bookLoan);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/viewBooks", method = RequestMethod.GET, produces="application/json")
	public List<Book> readAllBook(){
		try {
			List<Book> books = bookDao.readAllBook();
			for (Book b : books){
				b.setAuthors(authorDao.readAuthorByBook(b.getBookId()));
				b.setGenres(genreDao.readGenreByBook(b.getBookId()));
				b.setPublisher(Arrays.asList(publisherDao.readPublisherByBook(b.getBookId())));
			}
			return books;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewSingleBook/{bookID}", method = RequestMethod.GET, produces="application/json")
	public Book readBook(@PathVariable Integer bookID){
		try {
			Book book = bookDao.readBook(bookID);
			book.setAuthors(authorDao.readAuthorByBook(bookID));
			book.setGenres(genreDao.readGenreByBook(bookID));
			book.setPublisher(Arrays.asList(publisherDao.readPublisherByBook(book.getBookId())));
			return book;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewAuthors", method = RequestMethod.GET, produces="application/json")
	public List<Author> readAllAuthor(){
		try {
			List<Author> authors = authorDao.readAllAuthors();
			for (Author author : authors){
				author.setBooks(bookDao.readBookByAuthor(author.getAuthorID()));
				}
			return authors;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewAuthorsOnly", method = RequestMethod.GET, produces="application/json")
	public List<Author> readAllAuthorOnly(){
		try {
			List<Author> authors = authorDao.readAllAuthors();
/*			for (Author author : authors){
				author.setBooks(bookDao.readBookByAuthor(author.getAuthorID()));
				}*/
			return authors;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewSingleAuthor/{authorID}", method = RequestMethod.GET, produces="application/json")
	public Author readAuthor(@PathVariable Integer authorID){
		try {
			Author author = authorDao.readAuthor(authorID);
			author.setBooks(bookDao.readBookByAuthor(authorID));
			return author;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewAuthors/{authorName}", method = RequestMethod.GET, produces="application/json")
	public List<Author> readAuthor(@PathVariable  String authorName){
		try {
			List<Author> authors = authorDao.readAuthor(authorName);
			for (Author author : authors){
				author.setBooks(bookDao.readBookByAuthor(author.getAuthorID()));
				}
			return authors;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewGenres", method = RequestMethod.GET, produces="application/json")
	public List<Genre> readAllGenre(){
		try {
			List<Genre> genres = genreDao.readAllGenre();
			for (Genre genre : genres){
				genre.setBooks(bookDao.readBookByGenre(genre.getGenreId()));
				}
			return genres;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewGenresOnly", method = RequestMethod.GET, produces="application/json")
	public List<Genre> readAllGenreOnly(){
		try {
			List<Genre> genres = genreDao.readAllGenre();
/*			for (Genre genre : genres){
				genre.setBooks(bookDao.readBookByGenre(genre.getGenreId()));
				}*/
			return genres;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewSingleGenre/{genreID}", method = RequestMethod.GET, produces="application/json")
	public Genre readGenre(@PathVariable Integer genreID){
		try {
			Genre genre = genreDao.readGenre(genreID);
			genre.setBooks(bookDao.readBookByGenre(genreID));
			return genre;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewPublisher", method = RequestMethod.GET, produces="application/json")
	public List<Publisher> readAllPublisher(){
		try {
			List<Publisher> publishers = publisherDao.readAllPublisher();
			for (Publisher publisher : publishers){
				publisher.setBooks(bookDao.readBookByPublisher(publisher.getPublisherId()));
				}
			return publishers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewPublisherOnly", method = RequestMethod.GET, produces="application/json")
	public List<Publisher> readAllPublisherOnly(){
		try {
			List<Publisher> publishers = publisherDao.readAllPublisher();
/*			for (Publisher publisher : publishers){
				publisher.setBooks(bookDao.readBookByPublisher(publisher.getPublisherId()));
				}*/
			return publishers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewSinglePublisher/{publisherID}", method = RequestMethod.GET, produces="application/json")
	public Publisher readPublisher(@PathVariable Integer publisherID){
		try {
			Publisher publisher = publisherDao.readPublisher(publisherID);
			publisher.setBooks(bookDao.readBookByPublisher(publisherID));
			return publisher;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewBranch", method = RequestMethod.GET, produces="application/json")
	public List<Branch> readAllBranch(){
		try {
			List<Branch> branchs = branchDao.readAllBranch();
			for (Branch branch : branchs){
				branch.setBooks(bookDao.readBookByBranch(branch.getBranchID()));
				}
			return branchs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewSingleBranch/{branchID}", method = RequestMethod.GET, produces="application/json")
	public Branch readBranch(@PathVariable Integer branchID){	
		try {
			Branch branch = branchDao.readBranch(branchID);
			branch.setBooks(bookDao.readBookByBranch(branchID));
			return branch;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewBorrower", method = RequestMethod.GET, produces="application/json")
	public List<Borrower> readAllBorrower() {
		try {
			List<Borrower> borrowers = borrowerDao.readAllBorrower();
			return borrowers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewSingleBorrower/{borrowerID}", method = RequestMethod.GET, produces="application/json")
	public Borrower readBorrower(@PathVariable Integer borrowerID) {
		try {
			Borrower borrower = borrowerDao.readBorrower(borrowerID);
			return borrower;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewBookLoans", method = RequestMethod.GET, produces="application/json")
	public List<BookLoan> readAllBookLoans(){
		try {
			List<BookLoan> bookLoans = bookLoanDao.readAllBookLoan();
			for (BookLoan bookLoan : bookLoans){
				bookLoan.setBook(bookDao.readBook(bookLoan.getBookID()));
				bookLoan.setBorrower(borrowerDao.readBorrower(bookLoan.getBorrowerID()));
				bookLoan.setBranch(branchDao.readBranch(bookLoan.getBranchID()));
				}
			return bookLoans;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewSingleBookLoan", method = RequestMethod.POST,
			consumes="application/json", produces="application/json")
	public BookLoan readBookLoan(@RequestBody BookLoan bookLoan) {
		try {
			BookLoan bl = bookLoanDao.readBookLoan(bookLoan);
			bl.setBook(bookDao.readBook(bookLoan.getBookID()));
			bl.setBorrower(borrowerDao.readBorrower(bookLoan.getBorrowerID()));
			bl.setBranch(branchDao.readBranch(bookLoan.getBranchID()));
			return bl;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String parseListItems(List<?> items) {
		if (items == null || items.isEmpty()) {
			return "";
		}
		if (items.get(0).getClass() == Author.class) {
			String authorName = "";
			Author author = null;
			for (int i = 0; i < items.size(); i++){
				author = (Author) items.get(i);
				authorName += author.getAuthorName();
				if (i >= 0 && i < items.size()-1 && items.size()>1){
					authorName += " | ";
				}
			}
			return authorName;
		}
		if (items.get(0).getClass() == Genre.class){
			String genreName = "";
			for (int i = 0; i < items.size(); i++){
				Genre genre = (Genre) items.get(i);
				genreName += genre.getGenreName();
				if (i >= 0 && i < items.size()-1 && items.size()>1){
					genreName += " | ";
				}
			}
			return genreName;
		}
	return null;
	}
	
	public Integer countAuthors(){
		try {
			return authorDao.countAuthors();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}























