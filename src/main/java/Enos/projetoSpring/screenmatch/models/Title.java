package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.enums.EmployeePosition;
import Enos.projetoSpring.screenmatch.enums.Genre;

import java.util.ArrayList;
import java.util.List;

public class Title {
    private final List<Employee> employeeList;
    private final List<Genre> genreList;
    private final String title;
    private final Integer year;
    private final Integer runtime;
    private final String released;
    private final String sinpose;
    private final String language;
    private final String awards;
    private final String poster;
    private final Double rating;
    private final Integer totalVotes;
    private final String type;

    public Title(TitleData titleData){
        employeeList = new ArrayList<>();
        genreList = new ArrayList<>();
        this.title = titleData.title();
        this.year = getYearData(titleData.year());
        this.runtime = getRuntimeData(titleData.runtime());
        this.released = titleData.released();
        this.sinpose = titleData.plot();
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
                genreList.add(Genre.fromString(genre));
            }
        } else {
            genreList.add(Genre.fromString(g));
        }
    }

    public String printEmployees(){
        String msg = "";
        StringBuilder director = new StringBuilder("Director(s): \n");
        StringBuilder writer = new StringBuilder("Writer(s): \n");
        StringBuilder actor = new StringBuilder("Actor(s): \n");
        for (Employee employee : employeeList){
            if (employee.position() == EmployeePosition.DIRECTOR){
                director.append(employee.name()).append("\n");
            }
            if (employee.position() == EmployeePosition.WRITER){
                writer.append(employee.name()).append("\n");
            }
            if (employee.position() == EmployeePosition.ACTOR){
                actor.append(employee.name()).append("\n");
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

    private Integer getVotesData(String vote){
        String[] votes = vote.split(",");
        int i = 0;
        for(String v : votes){
            i += Integer.parseInt(v);
        }
        return i;
    }

    public String printGenres(){
        String msg = "";
        for (Genre genre : genreList){
            msg += genre + ", ";
        }
        return msg;
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

    public String getSinpose() {
        return sinpose;
    }

    public void addEmployee(Employee employee){
        if(employee != null){
            for (Employee e : employeeList){
                if(e.name().equalsIgnoreCase(employee.name()) && e.position() == employee.position()){
                    return;
                }
            }
            this.employeeList.add(employee);
        }
    }

    public Employee getEmployee(Employee employee){
        for (Employee e : employeeList){
            if(e.name().equalsIgnoreCase(employee.name()) && e.position() == employee.position()){
                return e;
            }
        }
        return null;
    }

    public Employee getEmployee(String name, EmployeePosition employeePosition){
        for (Employee e : employeeList){
            if(e.name().equalsIgnoreCase(name) && e.position() == employeePosition){
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
                "sinpose = " + sinpose + '\n' +
                "employeeList = \n" + printEmployees() +
                "}\n";
    }
}
