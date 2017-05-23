package com.gcit.libsystem.service;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.libsystem.dao.*;
import com.gcit.libsystem.entity.*;

@RestController
@RequestMapping(value = "/bor")
public class BorrowerService {
	
	@Autowired
	BookDao 	 bookDao;
	@Autowired
	BranchDao 	 branchDao;
	@Autowired
	AuthorDao 	 authorDao;
	@Autowired
	GenreDao 	 genreDao;
	@Autowired
	PublisherDao publisherDao;
	@Autowired
	BorrowerDao  borrowerDao;
	@Autowired
	BookLoanDao  bookLoanDao;
	@Autowired
	CopiesDao	copiesDao;
	
	@RequestMapping(value = "/viewBranch", method = RequestMethod.GET, produces="application/json")
	public List<Branch> readAllBranchs(){
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
	
	@RequestMapping(value = "/viewSingleBranchLimt/{branchID}", method = RequestMethod.GET, produces="application/json")
	public Branch readBranchWithLimit(@PathVariable Integer branchID){
		try {
			List<Book> books = bookDao.readBookByBranchWithLimit(branchID);
			for (Book b : books){
				b.setAuthors(authorDao.readAuthorByBook(b.getBookId()));
				b.setGenres(genreDao.readGenreByBook(b.getBookId()));
				b.setPublisher(publisherDao.readPublisherByBook(b.getBookId()));
				Integer copies = copiesDao.readCopies(b.getBookId(), branchID);
				b.setRelatedCopies(copies);
			}
			Branch branch = branchDao.readBranch(branchID);
			branch.setBooks(books);
			for (Book book : branch.getBooks()){
				Integer copies = copiesDao.readCopies(book.getBookId(), branchID);
				branch.setNoOfCopies(book.getBookId(), copies);
			}
			return branch;
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
			for (Book book : branch.getBooks()){
				Integer copies = copiesDao.readCopies(book.getBookId(), branchID);
				book.setRelatedCopies(copies);
				branch.setNoOfCopies(book.getBookId(), copies);
			}
			return branch;
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
			book.setPublisher(publisherDao.readPublisherByBook(bookID));
			return book;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/checkID/{cardID}", method = RequestMethod.GET, produces="application/json")
	public Boolean checkCardID(@PathVariable Integer cardID){
		try {
			return borrowerDao.checkBorrowerExist(cardID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@RequestMapping(value = "/checkOutBook", method = RequestMethod.POST, consumes="application/json")
	public void checkOutBook(@RequestBody BookLoan bookLoan){
		try {
			bookLoanDao.addBookLoan(bookLoan);
			branchDao.decNoOfCopies(readBranchWithLimit(bookLoan.getBranchID()),bookLoan.getBookID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/initBookLoan", method = RequestMethod.GET, produces="application/json")
	public BookLoan initBookLoan() {
		return new BookLoan();
	}

	@Transactional
	@RequestMapping(value = "/returnBook", method = RequestMethod.POST, consumes="application/json")
	public void returnBook(@RequestBody BookLoan bookLoan){
		try {
			bookLoanDao.returnBookLoanDate(bookLoan);
			Branch branch = readBranch(bookLoan.getBranchID());
			branchDao.incNoOfCopies(branch, bookLoan.getBookID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	@RequestMapping(value = "/viewSingleBookLoan/{borrowerID}", method = RequestMethod.GET, produces="application/json")
	public List<BookLoan> readBookLoan(@PathVariable Integer borrowerID){
		try {
			List<BookLoan> bookLoans = bookLoanDao.readBookLoan(borrowerID);
			for (BookLoan bookLoan : bookLoans){
				Book book = bookDao.readBook(bookLoan.getBookID());
				Branch branch = readBranch(bookLoan.getBranchID());
				Integer copies = copiesDao.readCopies(book.getBookId(), branch.getBranchID());
				book.setRelatedCopies(copies);
				branch.setNoOfCopies(book.getBookId(), copies);
				bookLoan.setBook(book);
				bookLoan.setBranch(branch);
			}
			return bookLoans;
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
}

//public boolean checkDupBook(Integer bookID, Integer branchID, Integer borrowerID) throws SQLException{
//	Connection conn = null;
//
//	try {
//		conn = ConnectionUtil.getConnection();
//		BookLoanDao bldao = new BookLoanDao(conn);
//		BranchDao brdao = new BranchDao(conn);		
//		return bldao.checkBookLoan(bookID, branchID, borrowerID);
//	} catch (ClassNotFoundException | SQLException e) {
//		e.printStackTrace();
//		return false;
//	} finally{
//		if(conn!=null){
//			conn.close();
//		}
//	}
//}

//public BookLoan readBookLoan(Integer borrowerID, Integer bookID, Integer branchID, Integer num) throws SQLException{
//Connection conn = null;
//
//try {
//	conn = ConnectionUtil.getConnection();
//	BookLoanDao bldao = new BookLoanDao(conn);
//	BookLoan bookLoan = bldao.readBookLoan(bookID, branchID, borrowerID, num);
//	return bookLoan;
//} catch (ClassNotFoundException | SQLException e) {
//	e.printStackTrace();
//} finally{
//	if(conn!=null){
//		conn.close();
//	}
//}
//return null;
//}

