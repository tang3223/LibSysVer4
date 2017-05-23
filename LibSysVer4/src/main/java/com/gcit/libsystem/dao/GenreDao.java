package com.gcit.libsystem.dao;

import org.springframework.jdbc.core.*;
import java.sql.*;
import java.util.*;

import com.gcit.libsystem.entity.Genre;

public class GenreDao extends BaseDao implements ResultSetExtractor<List<Genre>> {
	
	public void addGenre(Genre genre) throws SQLException {
		final String addGenre  = "INSERT INTO tbl_genre (genre_name) VALUE (?)";
		Object[] genreInfo = {genre.getGenreName()};
		template.update(addGenre, genreInfo);
	}
	
	public void updateGenre(Genre genre) throws SQLException{
		final String updateGenre = "UPDATE tbl_genre SET genre_name=? WHERE genre_id=?";
		Object[] genreInfo   = {genre.getGenreName(), genre.getGenreId()};
		template.update(updateGenre, genreInfo);
	}
	
	public void deleteGenre(Genre genre) throws SQLException{
		final String deleteGenre = "DELETE FROM tbl_genre WHERE genre_id=?";
		Object[] genreInfo   = {genre.getGenreId()};
		template.update(deleteGenre, genreInfo);
	}
	
	public List<Genre> readAllGenre() throws SQLException{
		final String readGenre = "SELECT * FROM tbl_genre";
		return template.query(readGenre, this);
	}
	
	public Genre readGenre(Integer genreID) throws SQLException{
		final String readGenre  = "SELECT * FROM tbl_genre WHERE genre_id=?";
		Object[] genreInfo = {genreID};
		List<Genre> genres = template.query(readGenre, genreInfo, this);
		if(genres!=null && !genres.isEmpty()){
			return genres.get(0);
		}
		return null;
	}
	
	public List<Genre> readGenre(String genreName) throws SQLException{
		final String readGenre = "SELECT * FROM tbl_genre WHERE genre_name LIKE ?";
		Object[] genreInfo = {"%" + genreName + "%"};
		return template.query(readGenre, genreInfo, this);
	}
	
	public List<Genre> readGenreByBook(Integer bookID) throws SQLException{
		final String readGenre  = "SELECT * FROM tbl_genre WHERE genre_id IN (SELECT genre_id FROM tbl_book_genres WHERE bookId=?)";
		Object[]     bookInfo = {(bookID)};
		return template.query(readGenre, bookInfo, this);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while(rs.next()){
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genres.add(genre);
		}
		return genres;
	}

}

//
//@Override
//public List<Genre> extractData(ResultSet rs) throws SQLException {
//	BookDao bookDao = new BookDao(conn);
//	List<Genre> genres = new ArrayList<>();
//	String readBook = "SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_genres WHERE genre_id=?)";
//	while(rs.next()){
//		Genre genre = new Genre();
//		genre.setGenreId(rs.getInt("genre_id"));
//		genre.setGenreName(rs.getString("genre_name"));
//		List<?> genreInfo = Arrays.asList(genre.getGenreId());
//		genre.setBooks(bookDao.readOnly(readBook,genreInfo));
//		genres.add(genre);
//	}
//	return genres;
//}
//@Override
//protected <T> T extractSingleData(ResultSet rs) throws SQLException {
//	return null;
//}
//
