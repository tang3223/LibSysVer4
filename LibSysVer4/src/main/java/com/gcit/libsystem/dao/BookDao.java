package com.gcit.libsystem.dao;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;


import java.sql.*;
import java.util.*;

import com.gcit.libsystem.entity.Book;

public class BookDao extends BaseDao implements ResultSetExtractor<List<Book>>{
	
	public void addBook(Book book) throws SQLException {
//		final String addBook  = "INSERT INTO tbl_book (title, pubId) VALUE (?,?)";
//		Object[]     bookInfo = {book.getTitle(),book.getPublisherID()};
//		template.update(addBook, bookInfo);	
		for (Integer authorID : book.getAuthorID()){
			addBookAuthors(book.getBookId(), authorID);
		}
		for (Integer genreID : book.getGenreID()){
			addBookGenres(book.getBookId(), genreID);
		}
	}
	
	public void addBookAuthors(Integer bookID, Integer authorID) throws SQLException {
		String   addBookAuthor  = "INSERT INTO tbl_book_authors VALUE (?,?)";
		Object[] bookAuthorInfo = {bookID, authorID};
		template.update(addBookAuthor, bookAuthorInfo);
	}
	
	public void addBookAuthors(Book book) throws SQLException{
		for (Integer authorID : book.getAuthorID()){
			addBookAuthors(book.getBookId(), authorID);
		} 
	}

	public void addBookGenres(Integer bookID, Integer genreID) throws SQLException {
		String  addBookGenres  = "INSERT INTO tbl_book_genres VALUE (?,?)";
		Object[] bookGenreInfo = {genreID, bookID};
		template.update(addBookGenres, bookGenreInfo);
	}
	
	public void addBookGenres(Book book) throws SQLException{
		for (Integer genreID : book.getGenreID()){
			addBookGenres(book.getBookId(), genreID);
		}
	}
	
	public Integer addBookReplyID(Book book) throws SQLException{
		final String addBook  = "INSERT INTO tbl_book (title, pubId) VALUE (?,?)";
		Object[] 	 bookInfo = {book.getTitle(), book.getPublisherID()};
		KeyHolder    bookID   = new GeneratedKeyHolder();
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(addBook, Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1, bookInfo[0]);
				ps.setObject(2, bookInfo[1]);
				return ps;
			}
		}, bookID);
		return bookID.getKey().intValue();
	}
	
	public void updateBook(Book book) throws SQLException{
		final String updateBook = "UPDATE tbl_book SET title=? WHERE bookId=?";
		Object[]     bookInfo   = {book.getTitle(), book.getBookId()};
		template.update(updateBook, bookInfo);
	}
	
	public void deleteBook(Book book) throws SQLException{
		final String deleteBook = "DELETE FROM tbl_book WHERE bookId=?";
		Object[]     bookInfo   = {book.getBookId()};
		template.update(deleteBook, bookInfo);
	}
	
	public void deleteBookAuthors(Book book) throws SQLException {
		String  deleteBookAuthor  = "DELETE FROM tbl_book_authors WHERE bookId=?";
		Object[] bookAuthorInfo = {book.getBookId()};
		template.update(deleteBookAuthor, bookAuthorInfo);
	}
	
	public void updateBookGenres(Integer genreID, Integer bookID) throws SQLException{
		String  updateBookGenres = "UPDATE tbl_book_genres SET genre_id=? WHERE bookId=?";
		Object[] bookGenreInfo = {genreID, bookID};
		template.update(updateBookGenres,bookGenreInfo);
	}
	
	public void deleteBookGenres(Book book) throws SQLException{
		String  deleteBookGenres = "DELETE FROM tbl_book_genres WHERE bookId=?";
		Object[] bookGenreInfo = {book.getBookId()};
		template.update(deleteBookGenres,bookGenreInfo);
	}
	
	public void updateBookPublisher(Book book) throws SQLException {
		String  updateBookPublisher  = "UPDATE tbl_book SET pubId=? WHERE bookId=?";
		Object[] bookPublisherInfo = {book.getPublisherID(), book.getBookId()};
		template.update(updateBookPublisher,bookPublisherInfo);
	}
	
	public List<Book> readAllBook() throws SQLException{
		final String readBook = "SELECT * FROM tbl_book";
		return template.query(readBook, this);
	}
	
	public Book readBook(Integer bookID) throws SQLException{
		final String readBook = "SELECT * FROM tbl_book WHERE bookId=?";
		Object[] 	 bookInfo = {bookID};
		List<Book>   books    = template.query(readBook, bookInfo, this);
		if (books != null && !books.isEmpty()) {
			return books.get(0);
		}
		return null;
	}
	
	public List<Book> readBook(String bookName) throws SQLException{
		final String readBook = "SELECT * FROM tbl_book WHERE title LIKE ?";
		Object[] 	 bookInfo = {("%" + bookName + "%")};
		return template.query(readBook, bookInfo, this);
	}
	
	public List<Book> readBookByAuthor(Integer authorID){
		final String readBook   = "SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId=?)";
		Object[]     authorInfo = {(authorID)};
		return template.query(readBook, authorInfo, this);
	}
	
	public List<Book> readBookByGenre(Integer genreID){
		final String readBook  = "SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_genres WHERE genre_id=?)";
		Object[]     genreInfo = {(genreID)};
		return template.query(readBook, genreInfo, this);
	}
	
	public List<Book> readBookByPublisher(Integer pubID){
		final String readBook  = "SELECT * FROM tbl_book WHERE pubId=?";
		Object[] publisherInfo = {(pubID)};
		return template.query(readBook, publisherInfo, this);
	}
	
	public List<Book> readBookByBranch(Integer branchID){
		final String readBook  = "SELECT * FROM tbl_book WHERE bookId "
				+ "IN (SELECT bookId  FROM tbl_book_copies WHERE branchId=?)";
		Object[] branchInfo = {(branchID)};
		return template.query(readBook, branchInfo, this);
	}
	
	public List<Book> readBookByBranchWithLimit(Integer branchID){
		final String readBook = "SELECT * FROM tbl_book WHERE bookId IN "
				+ "(SELECT bookId FROM tbl_book_copies WHERE branchId=? AND noOfCopies>0)";
		Object[] branchInfo = {(branchID)};
		return template.query(readBook, branchInfo, this);
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException{
		List<Book> books     = new ArrayList<>();
		while(rs.next()){
			Book book = new Book();
			book.setTitle(rs.getString("title"));
			book.setBookId(rs.getInt("bookId"));
			books.add(book);
		}
		return books;
	}
}




//public void addBookGenres(Book book) throws SQLException{
//for (int i = 0 ; i < book.getGenres().size(); i++){
//	addBookGenres(book.getGenres().get(i).getGenreId(), book.getBookId());
//}
//}
//

//public void addBookAuthors(Book book) throws SQLException {
//for (int i = 0 ; i < book.getAuthors().size(); i++){
//	addBookAuthors(book.getBookId(), book.getAuthors().get(i).getAuthorID());
//}
//}

//@Override
//protected <T> T extractSingleData(ResultSet rs) throws SQLException {
//	return null;
//}
