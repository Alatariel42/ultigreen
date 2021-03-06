package ultigreen.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ultigreen.demo.domain.ReusableQuestion;

@Service
public class ReusablesService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity addQuestions(ReusableQuestion question) {
    		String cols = "INSERT INTO reusables_footprint(user,plastic,paper,styrofoam,bagasse,date)" +
                    "values (?,?,?,?,?,?)";
    		jdbcTemplate.update(cols, question.getUser(), question.getPlastic(), question.getPaper(), question.getStyrofoam(), question.getBagasse(), question.getDate());
    		return new ResponseEntity("Successfully added", HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity getUpdatedData(String username) {
    		return new ResponseEntity(getRecentData(username), HttpStatus.OK);
    }
    
    @SuppressWarnings("rawtypes")
	public ResponseEntity makeUpdatedData(ReusableQuestion question) {
    		String sql ="UPDATE reusables_footprint SET plastic=?,paper=?,styrofoam=?,bagasse=? WHERE date=? and user=?";
    		jdbcTemplate.update(sql, question.getPlastic(), question.getPaper(), question.getStyrofoam(), question.getBagasse(),question.getDate(),question.getUser() );
    		return new ResponseEntity(HttpStatus.OK);
    }
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity calculateReusablesFootprint(String username) {
		double res = 0.0;
		int WEEKS = 52;
		double PLASTIC = 1.34;
		double PAPER = 0.125;
		double BAGASSE = 0.124;
		double STYROFOAM = 0.174;
		ReusableQuestion question = getRecentData(username);
		res += Integer.parseInt(question.getPlastic()) * WEEKS * PLASTIC;
		res += Integer.parseInt(question.getPaper()) * WEEKS * PAPER;
		res += Integer.parseInt(question.getBagasse()) * WEEKS * BAGASSE;
		res += Integer.parseInt(question.getStyrofoam()) * WEEKS * STYROFOAM;
		return new ResponseEntity(res, HttpStatus.OK);
	}
	
	public ReusableQuestion getRecentData(String username) {
		String sql = "SELECT * FROM reusables_footprint tf WHERE tf.user = ? ";
		RowMapper<ReusableQuestion> mapper = new BeanPropertyRowMapper<ReusableQuestion>(ReusableQuestion.class);
		List<ReusableQuestion> ans = jdbcTemplate.query(sql, mapper, username);
		return ans.get(ans.size()-1);
	}
}
