package com.gcit.libsystem.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.*;

import com.gcit.libsystem.dao.*;
import com.gcit.libsystem.entity.*;

@RestController
@RequestMapping(value = "/lib")
public class LibrarianService {
	
	@Autowired
	BranchDao 	 branchDao;
	@Autowired
	BookDao 	 bookDao;
	@Autowired
	AuthorDao 	 authorDao;
	@Autowired
	GenreDao 	 genreDao;
	@Autowired
	PublisherDao publisherDao;
	@Autowired
	CopiesDao	copiesDao;
	
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
	@RequestMapping(value = "/addNoOfCopies", method = RequestMethod.POST, consumes="application/json")
	public void addNoOfCopies(@RequestBody Branch branch){
		try {
			branch.setNoOfCopies(branch.getBookID(), branch.getCopies());
			branchDao.deleteNoOfCopies(branch.getBookID(), branch);
			branchDao.addNoOfCopies(branch.getBookID(), branch);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@RequestMapping(value = "/updateNoOfCopies", method = RequestMethod.POST, consumes="application/json")
	public void updateNoOfCopies(@RequestBody Branch branch){
		try {
			branch.setNoOfCopies(branch.getBookID(), branch.getCopies());
			branchDao.updateNoOfCopies(branch.getBookID(), branch);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
	
	@RequestMapping(value = "/viewBooks", method = RequestMethod.GET, produces="application/json")
	public List<Book> readAllBook(){
		try {
			List<Book> books = bookDao.readAllBook();
			for (Book b : books){
				b.setAuthors(authorDao.readAuthorByBook(b.getBookId()));
				b.setGenres(genreDao.readGenreByBook(b.getBookId()));
				b.setPublisher(publisherDao.readPublisherByBook(b.getBookId()));
			}
			return books;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	@RequestMapping(value = "/deleteNoOfCopies", method = RequestMethod.POST, consumes="application/json")
	public void deleteNoOfCopies(@RequestBody Branch branch){
		try {
			branchDao.deleteNoOfCopies(branch.getBookID(), branch);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}









