package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.enums.EmployeePosition;

import java.util.ArrayList;
import java.util.List;

public class Title {
    private List<Employee> employeeList;
    private String title;
    private Integer year;
    private Integer runtime;
    private String released;
    private String genre;
    private String sinpose;
    private String language;
    private String awards;
    private String poster;
    private Double rating;
    private Integer totalVotes;
    private String type;

    public Title(String title, Integer year, String genre, String language, Double rating, String sinpose, String awards, Integer totalVotes) {
        employeeList = new ArrayList<Employee>();
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.language = language;
        this.rating = rating;
        this.sinpose = sinpose;
        this.awards = awards;
        this.totalVotes = totalVotes;
    }

    public Title(TitlesData titlesData){
        employeeList = new ArrayList<Employee>();
        this.title = titlesData.title();
        this.year = getYearData(titlesData.year());
        this.runtime = getRuntimeData(titlesData.runtime());
        this.released = titlesData.released();
        this.genre = titlesData.genre();
        this.sinpose = titlesData.plot();
        this.language = titlesData.language();
        this.awards = titlesData.awards();
        this.poster = titlesData.poster();
        this.rating = getRatingData(titlesData.imdbRating());
        this.totalVotes = getVotesData(titlesData.imdbVotes());
        this.type = titlesData.type();
        addEmployeesData(titlesData.director(),EmployeePosition.DIRECTOR);
        addEmployeesData(titlesData.writer(),EmployeePosition.WRITER);
        addEmployeesData(titlesData.actors(),EmployeePosition.ACTOR);
    }

    public String printEmployees(){
        String msg = "";
        String director = "Director(s): \n";
        String writer = "Writer(s): \n";
        String actor = "Actor(s): \n";
        for (Employee employee : employeeList){
            if (employee.getPosition() == EmployeePosition.DIRECTOR){
                director += employee.getName() + "\n";
            }
            if (employee.getPosition() == EmployeePosition.WRITER){
                writer += employee.getName() + "\n";
            }
            if (employee.getPosition() == EmployeePosition.ACTOR){
                actor += employee.getName() + "\n";
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

    public String getGenre() {
        return genre;
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

    public String addEmployee(Employee employee){
        if(employee != null){
            for (Employee e : employeeList){
                if(e.getName().equalsIgnoreCase(employee.getName()) && e.getPosition() == employee.getPosition()){
                    return "Employee already exists in the list";
                }
            }
            this.employeeList.add(employee);
            return "Employee has been added!";
        }
        return "Employee is null";
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
                "genre = " + genre + '\n' +
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
