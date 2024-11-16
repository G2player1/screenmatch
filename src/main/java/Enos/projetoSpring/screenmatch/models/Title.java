package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.enums.EmployeePosition;
import Enos.projetoSpring.screenmatch.enums.GenreEnum;
import Enos.projetoSpring.screenmatch.models.omdbData.TitleData;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "titles")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title",unique = true,nullable = false)
    protected String title;
    @Column(name = "runtime")
    protected Integer runtime;
    @Column(name = "released")
    protected String released;
    @Column(name = "sinopse")
    protected String sinopse;
    @Column(name = "language")
    protected String language;
    @Column(name = "awards")
    protected String awards;
    @Column(name = "poster",nullable = false)
    protected String poster;
    @Column(name = "release_year")
    protected Integer year;
    @Column(name = "rating")
    protected Double rating;
    @Column(name = "total_votes")
    protected Integer totalVotes;
    @Column(name = "type")
    protected String type;
    @OneToMany(mappedBy = "title",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    protected List<Employee> employeeList;
    @OneToMany(mappedBy = "title",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    protected List<Genre> genreList;

    public Title(){}

    public Title(TitleData titleData){
        employeeList = new ArrayList<>();
        genreList = new ArrayList<>();
        this.title = titleData.title();
        this.year = getYearData(titleData.year());
        this.runtime = getRuntimeData(titleData.runtime());
        this.released = titleData.released();
        this.sinopse = titleData.plot();
        this.language = titleData.language();
        this.awards = titleData.awards();
        this.poster = titleData.poster();
        this.rating = getRatingData(titleData.imdbRating());
        this.totalVotes = getVotesData(titleData.imdbVotes());
        this.type = titleData.type();
        addEmployeesData(titleData.director(),EmployeePosition.DIRECTOR);
        addEmployeesData(titleData.writer(),EmployeePosition.WRITER);
        addEmployeesData(titleData.actors(),EmployeePosition.ACTOR);
        try {
            addGenres(titleData.genre());
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    private void addGenres(String g){
        if(g.contains(",")){
            String[] genres = g.split(", ");
            for(String genre : genres){
                this.addGenre(new Genre(GenreEnum.fromString(genre)));
            }
        } else {
            this.addGenre(new Genre(GenreEnum.fromString(g)));
        }
    }

    private void addGenre(Genre genre){
        for (Genre g : genreList){
            if(genre.getGenre() == g.getGenre()){
                return;
            }
        }
        genre.setTitle(this);
        this.genreList.add(genre);
    }

    public String printEmployees(){
        String msg = "";
        StringBuilder director = new StringBuilder("Director(s): \n");
        StringBuilder writer = new StringBuilder("Writer(s): \n");
        StringBuilder actor = new StringBuilder("Actor(s): \n");
        for (Employee employee : employeeList){
            if (employee.getPosition() == EmployeePosition.DIRECTOR){
                director.append(employee.getName()).append("\n");
            }
            if (employee.getPosition() == EmployeePosition.WRITER){
                writer.append(employee.getName()).append("\n");
            }
            if (employee.getPosition() == EmployeePosition.ACTOR){
                actor.append(employee.getName()).append("\n");
            }
        }
        msg += director;
        msg += writer;
        msg += actor;
        return msg;
    }

    private void addEmployeesData(String emps, EmployeePosition employeePosition){
        if(emps.contains(",")){
            String[] employees = emps.split(", ");
            for(String employee : employees){
                addEmployee(new Employee(employee,employeePosition));
            }
        } else {
            addEmployee(new Employee(emps,employeePosition));
        }
    }

    private Double getRatingData(String rating){
        if(rating.equalsIgnoreCase("n/a")){
            return null;
        } else {
            return Double.parseDouble(rating);
        }
    }

    private Integer getRuntimeData(String runtime){
        if(runtime.equalsIgnoreCase("n/a")){
            return null;
        } else {
            return Integer.parseInt(runtime.replaceAll("([^0-9]+)",""));
        }
    }

    private Integer getYearData(String year){
        if(year.contains("–")){
            return Integer.parseInt(year.substring(0,year.indexOf("–")));
        } else if (year.equalsIgnoreCase("n/a")){
            return null;
        } else {
            return Integer.parseInt(year);
        }
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public boolean containsGenre(Genre genre){
        for (Genre g : genreList){
            if (g.getGenre().equals(genre.getGenre())){
                return true;
            }
        }
        return false;
    }

    private Integer getVotesData(String vote){
        if(vote.equalsIgnoreCase("n/a")){
            return null;
        }
        String[] votes = vote.split(",");
        int i = 0;
        for(String v : votes){
            i += Integer.parseInt(v);
        }
        return i;
    }

    public String printGenres(){
        StringBuilder msg = new StringBuilder();
        for (Genre genre : genreList) {
            msg.append(genre.getGenre()).append(", ");
        }
        return msg.toString();
    }

    public String getAwards() {
        return awards;
    }

    public Integer getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public String getTitle() {
        return title;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public String getPoster() {
        return poster;
    }

    public String getLanguage() {
        return language;
    }

    public Double getRating() {
        return rating;
    }

    public String getReleased() {
        return released;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void addEmployee(Employee employee){
        if(employee != null){
            for (Employee e : employeeList){
                if(e.getName().equalsIgnoreCase(employee.getName()) && e.getPosition() == employee.getPosition()){
                    return;
                }
            }
            employee.setTitle(this);
            this.employeeList.add(employee);
        }
    }

    public Employee getEmployee(Employee employee){
        for (Employee e : employeeList){
            if(e.getName().equalsIgnoreCase(employee.getName()) && e.getPosition() == employee.getPosition()){
                return e;
            }
        }
        return null;
    }

    public Employee getEmployee(String name, EmployeePosition employeePosition){
        for (Employee e : employeeList){
            if(e.getName().equalsIgnoreCase(name) && e.getPosition() == employeePosition){
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Title{\n" +
                "poster = " + poster + '\n' +
                "title = " + title + "\n" +
                "genres = " + printGenres() + "\n" +
                "rating = " + rating + "\n" +
                "totalVotes = " + totalVotes + "\n" +
                "year = " + year + "\n" +
                "released = " + released + '\n' +
                "runtime = " + runtime + "\n" +
                "awards = " + awards + '\n' +
                "language = " + language + '\n' +
                "type = " + type + '\n' +
                "sinpose = " + sinopse + '\n' +
                "employeeList = \n" + printEmployees() +
                "}\n";
    }
}
