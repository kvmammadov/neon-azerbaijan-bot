package com.kv.ms.bot.neonazerbaijan.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", unique = true, nullable = false)
    private String postId;

    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "media_url", length = 2048)
    private String mediaUrl;

    @Column(name = "caption", length = 2048)
    private String caption;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @Column(name = "profile_id", nullable = false)
    private String profileId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
//    public static PostEntity of(String postId, String mediaType, String mediaUrl,
//                                String caption, Boolean isPublished, String profileId) {
//        return new PostEntity()
//                .setPostId(postId)
//                .setMediaType(mediaType)
//                .setMediaUrl(mediaUrl)
//                .setCaption(caption)
//                .setProfileId(profileId)
//                .setIsPublished(isPublished);
//    }

