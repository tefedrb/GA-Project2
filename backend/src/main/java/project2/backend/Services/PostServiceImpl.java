package project2.backend.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project2.backend.Models.Person;
import project2.backend.Models.Post;
import project2.backend.Models.PostPersonObj;
import project2.backend.Repositories.PersonRepository;
import project2.backend.Repositories.PostRepository;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @Autowired
    PostService postService;

    @Override
    public Post createPost(Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Person person = personService.getPerson(currentPrincipalName);
        post.setPerson(person);
        person.addPost(post);
        return postRepository.save(post);
    }

    @Override
    public void deletePostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        Person person = personRepository.findById(post.getPerson().getId()).get();
        person.getPosts().remove(post);
        postRepository.delete(post);
    }

    @Override

    public List<PostPersonObj> listAllPosts(){
        //return postRepository.findAll();
        return postRepository.listPosts();
    }

    @Override
    public Iterable<Post> findAllPostsByPerson(Long personId){
        System.out.println("we are here ->>> " + personId);
        return postRepository.findAllPostsByPerson(personId);
    }
}
